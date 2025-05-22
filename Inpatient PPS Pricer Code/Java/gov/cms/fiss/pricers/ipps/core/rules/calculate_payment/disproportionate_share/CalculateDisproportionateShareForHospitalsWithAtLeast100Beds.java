package gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.disproportionate_share;

import gov.cms.fiss.pricers.common.api.InpatientProviderData;
import gov.cms.fiss.pricers.ipps.core.CbsaReference;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import org.apache.commons.lang3.StringUtils;

/**
 * Calculates the operating disproportionate share hospital for claims with a CBSA size of "O" or
 * "L" and for a facility with at least 100 inpatient beds. The share value has no cap.
 *
 * <p>Implements the following logic (the calculation is delegated to the superclass, as it is
 * common code).
 *
 * <pre>
 * ***********************************************************
 * **2**   100 + BEDS
 * ***  NO CAP &gt;&gt; CAN EXCEED 12%
 *       IF (W-CBSA-SIZE = 'O' OR 'L') AND P-NEW-BED-SIZE &gt; 99
 *                                AND H-WK-OPER-DSH &gt; .1499
 *                                AND H-WK-OPER-DSH &lt; .2020
 *         COMPUTE H-OPER-DSH ROUNDED = (H-WK-OPER-DSH - .15)
 *                                       * .65 + .025.
 *       IF (W-CBSA-SIZE = 'O' OR 'L') AND P-NEW-BED-SIZE &gt; 99
 *                                AND H-WK-OPER-DSH &gt; .2019
 *         COMPUTE H-OPER-DSH ROUNDED = (H-WK-OPER-DSH - .202)
 *                                       * .825 + .0588.
 * </pre>
 *
 * <p>Converted from {@code 3900A-CALC-OPER-DSH} in the COBOL code (continued).
 *
 * @since 2019
 */
public class CalculateDisproportionateShareForHospitalsWithAtLeast100Beds
    extends CalculateDisproportionateShareWithoutCap {

  @Override
  public boolean shouldExecute(IppsPricerContext calculationContext) {
    final InpatientProviderData providerData = calculationContext.getProviderData();
    final CbsaReference cbsaReference = calculationContext.getCbsaReference();

    return StringUtils.equalsAny(cbsaReference.getSize(), "O", "L")
        && providerData.getBedSize() > 99;
  }
}
