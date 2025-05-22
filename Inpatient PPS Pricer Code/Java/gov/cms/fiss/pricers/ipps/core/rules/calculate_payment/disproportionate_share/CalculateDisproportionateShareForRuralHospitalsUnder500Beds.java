package gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.disproportionate_share;

import gov.cms.fiss.pricers.common.api.InpatientProviderData;
import gov.cms.fiss.pricers.ipps.core.CbsaReference;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import org.apache.commons.lang3.StringUtils;

/**
 * Calculates the operating disproportionate share hospital for claims with a CBSA size of "R" and
 * for a facility with less than 500 inpatient beds. The share value will be capped at 12% (0.1200).
 *
 * <p>Implements the following logic (the calculation is delegated to the superclass, as it is
 * common code).
 *
 * <pre>
 * ***********************************************************
 * **3**   OTHER RURAL HOSPITALS LESS THEN 500 BEDS
 * ***  NOT TO EXCEED 12%
 *       IF W-CBSA-SIZE = 'R'     AND P-NEW-BED-SIZE &lt; 500
 *                                AND H-WK-OPER-DSH &gt; .1499
 *                                AND H-WK-OPER-DSH &lt; .2020
 *         COMPUTE H-OPER-DSH ROUNDED = (H-WK-OPER-DSH - .15)
 *                                  * .65 + .025
 *         IF H-OPER-DSH &gt; .1200
 *               MOVE .1200 TO H-OPER-DSH.
 *       IF W-CBSA-SIZE = 'R'     AND P-NEW-BED-SIZE &lt; 500
 *                                AND H-WK-OPER-DSH &gt; .2019
 *         COMPUTE H-OPER-DSH ROUNDED = (H-WK-OPER-DSH - .202)
 *                                  * .825 + .0588
 *         IF H-OPER-DSH &gt; .1200
 *                  MOVE .1200 TO H-OPER-DSH.
 * </pre>
 *
 * <p>Converted from {@code 3900A-CALC-OPER-DSH} in the COBOL code (continued).
 *
 * @since 2019
 */
public class CalculateDisproportionateShareForRuralHospitalsUnder500Beds
    extends CalculateDisproportionateShareWithCap {

  @Override
  public boolean shouldExecute(IppsPricerContext calculationContext) {
    final InpatientProviderData providerData = calculationContext.getProviderData();
    final CbsaReference cbsaReference = calculationContext.getCbsaReference();

    return StringUtils.equals(cbsaReference.getSize(), "R") && providerData.getBedSize() < 500;
  }
}
