package gov.cms.fiss.pricers.ltch.resources;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import gov.cms.fiss.pricers.common.application.resources.BaseCsvResource;
import gov.cms.fiss.pricers.ltch.LtchPricerConfiguration;
import gov.cms.fiss.pricers.ltch.api.v1.InpatientDrgRetrievalResponse;
import gov.cms.fiss.pricers.ltch.api.v1.InpatientDrgsTableEntry;
import gov.cms.fiss.pricers.ltch.core.tables.DataTables;
import io.dropwizard.jersey.jsr310.LocalDateParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/v1/ip-drg-entries")
public class InpatientDrgEntryResource extends BaseCsvResource<InpatientDrgsTableEntry> {
  public InpatientDrgEntryResource(LtchPricerConfiguration pricerConfiguration) {
    super(
        pricerConfiguration,
        csvMapper -> csvMapper.schemaFor(InpatientDrgsTableEntry.class).withHeader());
  }

  @Override
  protected void extractAndPopulate(CsvMapper csvMapper, CsvSchema csvSchema, Integer pricerYear) {
    final Optional<Map<String, NavigableMap<LocalDate, InpatientDrgsTableEntry>>> matchingData =
        Optional.ofNullable(DataTables.forYear(pricerYear).getIppsDrgsMap());

    if (matchingData.isPresent()) {
      final List<InpatientDrgsTableEntry> entries =
          matchingData.map(Map::entrySet).stream()
              .flatMap(s -> s.stream().map(Entry::getValue))
              .map(Map::entrySet)
              .flatMap(Collection::stream)
              .map(Entry::getValue)
              .sorted(
                  Comparator.comparing(InpatientDrgsTableEntry::getDiagnosticRelatedGroup)
                      .thenComparing(InpatientDrgsTableEntry::getEffectiveDate))
              .collect(Collectors.toList());
      addEntries(pricerYear, entries);

      try {
        addCsv(pricerYear, csvMapper.writer(csvSchema).writeValueAsString(entries));
      } catch (final JsonProcessingException jpe) {
        throw new IllegalStateException(
            "Failed to convert entries to CSV for year " + pricerYear, jpe);
      }
    }
  }

  @GET
  @Operation(
      summary =
          "Retrieves all inpatient diagnostic-related group (DRG) data for a given fiscal year.",
      description =
          "Retrieves all inpatient DRG data for all effective dates within the provided fiscal "
              + "year.",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "The matching inpatient DRG data",
            content = {
              @Content(
                  mediaType = MediaType.APPLICATION_JSON,
                  schema = @Schema(implementation = InpatientDrgRetrievalResponse.class)),
              @Content(mediaType = MEDIA_TYPE_CSV)
            }),
        @ApiResponse(responseCode = "400", description = "Invalid request"),
        @ApiResponse(responseCode = "404", description = "Year not found"),
        @ApiResponse(responseCode = "406", description = "Unsupported media type requested"),
        @ApiResponse(responseCode = "500", description = "Data retrieval error")
      })
  @Path("{year}")
  @Produces({MediaType.APPLICATION_JSON, MEDIA_TYPE_CSV})
  @Timed
  public Response findAll(
      @Max(9999)
          @Min(2000)
          @NotNull
          @PathParam("year")
          @Parameter(description = "The fiscal year for which data will be retrieved.")
          int year,
      @HeaderParam(HttpHeaders.ACCEPT) String contentType) {
    return getPerYearEntries(year, contentType, InpatientDrgRetrievalResponse::new);
  }

  @GET
  @Operation(
      summary = "Retrieve all matching inpatient DRG data for a given year.",
      description =
          "Returns the data for a specific inpatient DRG for matching effective dates within the "
              + "provided fiscal year.",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "The matching DRG data",
            content =
                @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = InpatientDrgRetrievalResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid request"),
        @ApiResponse(responseCode = "404", description = "Year or specified DRG not found"),
        @ApiResponse(responseCode = "500", description = "Data retrieval error")
      })
  @Path("{year}/{drg}")
  @Produces(MediaType.APPLICATION_JSON)
  @Timed
  public Response findMatching(
      @Max(9999)
          @Min(2000)
          @NotNull
          @Parameter(
              description = "The fiscal year for which data will be retrieved.",
              required = true)
          @PathParam("year")
          int year,
      @NotNull
          @Parameter(
              description =
                  "The inpatient diagnostic-related group for which data will be retrieved.",
              required = true)
          @PathParam("drg")
          @Pattern(regexp = "^\\d{3}$")
          String diagnosticRelatedGroup,
      @Parameter(
              description =
                  "Filters data so that only the inpatient DRG data effective as of the provided "
                      + "date will be returned; if omitted, all values for the year will be "
                      + "returned.",
              example = "2020-04-01",
              schema = @Schema(implementation = LocalDate.class))
          @QueryParam("effectiveDate")
          LocalDateParam effectiveDate) {
    return findMatchingEntries(
        () -> {
          if (null != effectiveDate) {
            final Optional<InpatientDrgsTableEntry> matchedEntry =
                Optional.ofNullable(DataTables.forYear(year))
                    .map(
                        dt -> dt.getInpatientDrgEntry(diagnosticRelatedGroup, effectiveDate.get()));

            return matchedEntry.map(Collections::singletonList).orElseGet(ArrayList::new);
          } else {
            final Optional<Collection<InpatientDrgsTableEntry>> matchingData =
                Optional.ofNullable(DataTables.forYear(year))
                    .map(DataTables::getIppsDrgsMap)
                    .map(ct -> ct.get(diagnosticRelatedGroup))
                    .map(Map::values);

            // Check if year is present
            return matchingData.map(ArrayList::new).orElseGet(ArrayList::new);
          }
        },
        InpatientDrgRetrievalResponse::new);
  }
}
