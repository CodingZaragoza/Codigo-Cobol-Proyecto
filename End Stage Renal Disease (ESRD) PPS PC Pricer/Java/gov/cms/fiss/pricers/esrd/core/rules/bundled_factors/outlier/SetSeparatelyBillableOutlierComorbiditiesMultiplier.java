package gov.cms.fiss.pricers.esrd.core.rules.bundled_factors.outlier;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.esrd.api.v2.BundledPaymentData;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import gov.cms.fiss.pricers.esrd.core.codes.PayerOnlyConditionCode;
import org.apache.commons.lang3.StringUtils;

/**
 * Sets the separately-billable outlier comorbidity adjustment factor.
 *
 * <pre>
 * *****************************************************************
 * **  Set separately billable OUTLIER Co-morbidities adjustment ***
 * CY 2016 DROPPED MB &amp; MF
 * *****************************************************************
 * </pre>
 *
 * <p>Converted from {@code 2500-CALC-OUTLIER-FACTORS} (continued) in the COBOL code.
 *
 * @since 2020
 */
public class SetSeparatelyBillableOutlierComorbiditiesMultiplier
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    final BundledPaymentData bundledData = calculationContext.getBundledData();

    //     IF COMORBID-CWF-RETURN-CODE = SPACES  THEN
    //        IF H-PATIENT-AGE  <  18  THEN
    //           MOVE 1.000                  TO
    //                                       H-OUT-COMORBID-MULTIPLIER
    //           MOVE '10'                   TO PPS-2011-COMORBID-PAY
    //        ELSE
    //           IF H-BUN-ONSET-FACTOR  =  CM-ONSET-LE-120  THEN
    //              MOVE 1.000               TO
    //                                       H-OUT-COMORBID-MULTIPLIER
    //              MOVE '10'                TO PPS-2011-COMORBID-PAY
    //           ELSE
    //              PERFORM 2600-CALC-COMORBID-OUT-ADJUST
    //           END-IF
    //        END-IF
    //     ELSE
    //        IF COMORBID-CWF-RETURN-CODE  =  '10'  THEN
    //           MOVE 1.000                  TO
    //                                       H-OUT-COMORBID-MULTIPLIER
    //        ELSE
    //           IF COMORBID-CWF-RETURN-CODE  =  '20'  THEN
    //              MOVE SB-GI-BLEED         TO
    //                                       H-OUT-COMORBID-MULTIPLIER
    //              ELSE
    //                 IF COMORBID-CWF-RETURN-CODE  =  '40'  THEN
    //                    MOVE SB-PERICARDITIS TO
    //                                       H-OUT-COMORBID-MULTIPLIER
    //                 END-IF
    //           END-IF
    //        END-IF
    //     END-IF.
    if (StringUtils.isEmpty(calculationContext.getCwfReturnCode())) {
      if (!calculationContext.isAdultPatient()) {
        calculationContext.setOutlierComorbidityMultiplier(EsrdPricerContext.DEFAULT_MULTIPLIER);

        bundledData.setComorbidityPaymentCode(EsrdPricerContext.COMORBIDITY_RETURN_CODE_DEFAULT_10);
      } else {
        if (BigDecimalUtils.equals(
            calculationContext.getBundledOnsetFactor(),
            calculationContext.getCaseMixOnsetLessThanOrEqualTo120Multiplier())) {
          calculationContext.setOutlierComorbidityMultiplier(EsrdPricerContext.DEFAULT_MULTIPLIER);

          bundledData.setComorbidityPaymentCode(
              EsrdPricerContext.COMORBIDITY_RETURN_CODE_DEFAULT_10);
        } else {
          calculateOutlierComorbidityAdjustment(calculationContext);
        }
      }
    } else {
      switch (calculationContext.getCwfReturnCode()) {
        case EsrdPricerContext.COMORBIDITY_RETURN_CODE_DEFAULT_10:
          calculationContext.setOutlierComorbidityMultiplier(EsrdPricerContext.DEFAULT_MULTIPLIER);

          break;
        case EsrdPricerContext.COMORBIDITY_RETURN_CODE_GASTROENTERITIS_20:
          calculationContext.setOutlierComorbidityMultiplier(
              calculationContext.getSeparatelyBillableGastrointestinalBleedMultiplier());

          break;
        case EsrdPricerContext.COMORBIDITY_RETURN_CODE_PERICARDITIS_40:
          calculationContext.setOutlierComorbidityMultiplier(
              calculationContext.getSeparatelyBillablePericarditisMultiplier());

          break;
        default:
          break;
      }
    }
  }

  /**
   * Calculates the outlier comorbidity adjustment.
   *
   * <pre>
   * *****************************************************************
   * **  Calculate OUTLIER Co-morbidities adjustment               ***
   * *****************************************************************
   *  This logic assumes that the comorbids are randomly assigned    *
   * to the comorbid table.  It will select the highest comorbid for *
   * payment if one is found. CY 2016 DROPPED MB &amp; MF            *
   * *****************************************************************
   * </pre>
   *
   * <p>Converted from {@code 2600-CALC-COMORBID-OUT-ADJUST} in the COBOL code.
   *
   * @param calculationContext the current execution context
   */
  protected void calculateOutlierComorbidityAdjustment(EsrdPricerContext calculationContext) {
    final BundledPaymentData bundledData = calculationContext.getBundledData();

    //     MOVE 'N'                          TO IS-HIGH-COMORBID-FOUND.
    //     MOVE 1.000                        TO
    //                                  H-OUT-COMORBID-MULTIPLIER.
    calculationContext.setOutlierComorbidityMultiplier(EsrdPricerContext.DEFAULT_MULTIPLIER);

    //     PERFORM VARYING  SUB  FROM  1 BY 1
    //       UNTIL SUB   >  6   OR   HIGH-COMORBID-FOUND
    for (final String comorbidityCode : calculationContext.getComorbidityCodes()) {
      if (null != comorbidityCode) {
        processComorbidityCode(calculationContext, bundledData, comorbidityCode);
      }
    }
  }

  /**
   * Handles processing for an individual comorbidity code.
   *
   * @param calculationContext the current context
   * @param bundledData the output bundled data
   * @param comorbidityCode the current code
   */
  protected void processComorbidityCode(
      EsrdPricerContext calculationContext,
      BundledPaymentData bundledData,
      String comorbidityCode) {
    final PayerOnlyConditionCode payerOnlyConditionCode =
        PayerOnlyConditionCode.valueOf(comorbidityCode);

    //         IF COMORBID-DATA (SUB) = 'MA'  THEN
    //           MOVE SB-GI-BLEED            TO
    //                                  H-OUT-COMORBID-MULTIPLIER
    //           MOVE "Y"                    TO ACUTE-COMORBID-TRACK
    //           ELSE
    //             IF COMORBID-DATA (SUB) = 'MC'  THEN
    //                IF SB-PERICARDITIS  >
    //                                  H-OUT-COMORBID-MULTIPLIER  THEN
    //                  MOVE SB-PERICARDITIS TO
    //                                  H-OUT-COMORBID-MULTIPLIER
    //                  MOVE "Y"             TO ACUTE-COMORBID-TRACK
    //                END-IF
    //             ELSE
    //               IF COMORBID-DATA (SUB) = 'MD'  THEN
    //                 IF SB-MYELODYSPLASTIC  >
    //                                  H-OUT-COMORBID-MULTIPLIER  THEN
    //                   MOVE SB-MYELODYSPLASTIC  TO
    //                                  H-OUT-COMORBID-MULTIPLIER
    //                   MOVE "Y"            TO CHRONIC-COMORBID-TRACK
    //                 END-IF
    //               ELSE
    //                 IF COMORBID-DATA (SUB) = 'ME'  THEN
    //                   IF SB-SICKEL-CELL  >
    //                                 H-OUT-COMORBID-MULTIPLIER  THEN
    //                     MOVE SB-SICKEL-CELL  TO
    //                                  H-OUT-COMORBID-MULTIPLIER
    //                      MOVE "Y"          TO CHRONIC-COMORBID-TRACK
    //                   END-IF
    //                 END-IF
    //               END-IF
    //             END-IF
    //         END-IF
    //     END-PERFORM.
    switch (payerOnlyConditionCode) {
      case MA:
        calculationContext.setOutlierComorbidityMultiplier(
            calculationContext.getSeparatelyBillableGastrointestinalBleedMultiplier());

        calculationContext.setAcuteComorbidityFound(true);

        break;
      case MC:
        if (BigDecimalUtils.isGreaterThan(
            calculationContext.getSeparatelyBillablePericarditisMultiplier(),
            calculationContext.getOutlierComorbidityMultiplier())) {
          calculationContext.setOutlierComorbidityMultiplier(
              calculationContext.getSeparatelyBillablePericarditisMultiplier());

          calculationContext.setAcuteComorbidityFound(true);
        }

        break;
      case MD:
        if (BigDecimalUtils.isGreaterThan(
            calculationContext.getSeparatelyBillableMyelodysplasticSyndromeMultiplier(),
            calculationContext.getOutlierComorbidityMultiplier())) {

          calculationContext.setOutlierComorbidityMultiplier(
              calculationContext.getSeparatelyBillableMyelodysplasticSyndromeMultiplier());

          calculationContext.setChronicComorbidityFound(true);
        }

        break;
      case ME:
        if (BigDecimalUtils.isGreaterThan(
            calculationContext.getSeparatelyBillableSickleCellMultiplier(),
            calculationContext.getOutlierComorbidityMultiplier())) {
          calculationContext.setOutlierComorbidityMultiplier(
              calculationContext.getSeparatelyBillableSickleCellMultiplier());

          calculationContext.setChronicComorbidityFound(true);
        }

        break;
      default:
        break;
    }
  }
}
