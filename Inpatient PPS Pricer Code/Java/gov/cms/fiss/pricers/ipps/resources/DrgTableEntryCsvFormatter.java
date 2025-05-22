package gov.cms.fiss.pricers.ipps.resources;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

/** Provides formatting hints for the Jackson CSV conversion; used as a mix-in. */
public interface DrgTableEntryCsvFormatter {
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  LocalDate getEffectiveDate();
}
