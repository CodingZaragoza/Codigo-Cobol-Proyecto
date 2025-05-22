package gov.cms.fiss.pricers.ipps.core.rules.calculate_payment.disproportionate_share;

import gov.cms.fiss.pricers.common.api.InpatientProviderData;
import gov.cms.fiss.pricers.ipps.core.IppsPricerContext;
import org.apache.commons.lang3.StringUtils;

/**
 * Calculates the operating disproportionate share hospital for claims with a provider type of 7,
 * 14, 15, 17, or 22. The share value has no cap.
 *
 * <p>Implements the following logic (the calculation is delegated to the superclass, as it is
 * common code).
 *
 * <pre>
 * ***********************************************************
 * **6**   RURAL HOSPITALS RRC   RULE 5 &amp; 6 SAME
 * ***  RRC OVERRIDES SCH CAP
 * ***  NO CAP &gt;&gt; CAN EXCEED 12%
 *          IF (P-NEW-PROVIDER-TYPE = '07' OR '14' OR '15' OR
 *                                    '17' OR '22')
 *                                AND H-WK-OPER-DSH &gt; .1499
 *                                AND H-WK-OPER-DSH &lt; .2020
 *          COMPUTE H-OPER-DSH ROUNDED = (H-WK-OPER-DSH - .15)
 *                                  * .65 + .025.
 *          IF (P-NEW-PROVIDER-TYPE = '07' OR '14' OR '15' OR
 *                                    '17' OR '22')
 *                                AND H-WK-OPER-DSH &gt; .2019
 *          COMPUTE H-OPER-DSH ROUNDED = (H-WK-OPER-DSH - .202)
 *                                  * .825 + .0588.
 *       COMPUTE H-OPER-DSH ROUNDED = H-OPER-DSH * 1.0000.
 * </pre>
 *
 * <p>Converted from {@code 3900A-CALC-OPER-DSH} in the COBOL code (continued).
 *
 * @since 2019
 */
public class CalculateDisproportionateShareForRuralRrcProviders
    extends CalculateDisproportionateShareWithoutCap {
  private static final String[] MATCHED_TYPES = new String[] {"07", "14", "15", "17", "22"};

  @Override
  public boolean shouldExecute(IppsPricerContext calculationContext) {
    final InpatientProviderData providerData = calculationContext.getProviderData();

    return StringUtils.equalsAny(providerData.getProviderType(), MATCHED_TYPES);
  }
}
