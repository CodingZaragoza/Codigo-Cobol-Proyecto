package gov.cms.fiss.pricers.esrd.core.rules.bundled_factors;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.common.util.BigDecimalUtils;
import gov.cms.fiss.pricers.esrd.api.v2.BundledPaymentData;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import gov.cms.fiss.pricers.esrd.core.codes.PayerOnlyConditionCode;
import org.apache.commons.lang3.StringUtils;

/**
 * Determines the comorbidities adjustment factor.
 *
 * <pre>
 * *****************************************************************
 * **  Set BUNDLED Co-morbidities adjustment                     ***
 * *****************************************************************
 * </pre>
 *
 * <p>Converted from {@code 2000-CALCULATE-BUNDLED-FACTORS} (continued) in the COBOL code.
 *
 * @since 2020
 */
public class SetBundledComorbiditiesAdjustment
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    final BundledPaymentData bundledData = calculationContext.getBundledData();

    //     IF COMORBID-CWF-RETURN-CODE = SPACES  THEN
    //        IF H-PATIENT-AGE  <  18  THEN
    //           MOVE 1.000                  TO
    //                                       H-BUN-COMORBID-MULTIPLIER
    //           MOVE '10'                   TO PPS-2011-COMORBID-PAY
    //        ELSE
    //           IF H-BUN-ONSET-FACTOR  =  CM-ONSET-LE-120  THEN
    //              MOVE 1.000               TO
    //                                       H-BUN-COMORBID-MULTIPLIER
    //              MOVE '10'                TO PPS-2011-COMORBID-PAY
    //           ELSE
    //              PERFORM 2100-CALC-COMORBID-ADJUST
    //              MOVE H-COMORBID-MULTIPLIER TO
    //                                       H-BUN-COMORBID-MULTIPLIER
    //           END-IF
    //        END-IF
    //     ELSE
    //        IF COMORBID-CWF-RETURN-CODE  =  '10'  THEN
    //           MOVE 1.000                  TO
    //                                       H-BUN-COMORBID-MULTIPLIER
    //           MOVE '10'                   TO PPS-2011-COMORBID-PAY
    //        ELSE
    //           IF COMORBID-CWF-RETURN-CODE  =  '20'  THEN
    //              MOVE CM-GI-BLEED         TO
    //                                       H-BUN-COMORBID-MULTIPLIER
    //              MOVE '20'                TO PPS-2011-COMORBID-PAY
    //                 END-IF
    //             ELSE
    //                 IF COMORBID-CWF-RETURN-CODE  =  '40'  THEN
    //                    MOVE CM-PERICARDITIS TO
    //                                       H-BUN-COMORBID-MULTIPLIER
    //                    MOVE '40'          TO PPS-2011-COMORBID-PAY
    //           END-IF
    //        END-IF
    //     END-IF.
    if (StringUtils.isEmpty(calculationContext.getCwfReturnCode())) {
      if (!calculationContext.isAdultPatient()) {
        calculationContext.setBundledComorbidityMultiplier(EsrdPricerContext.DEFAULT_MULTIPLIER);

        bundledData.setComorbidityPaymentCode(EsrdPricerContext.COMORBIDITY_RETURN_CODE_DEFAULT_10);
      } else {
        if (BigDecimalUtils.equals(
            calculationContext.getBundledOnsetFactor(),
            calculationContext.getCaseMixOnsetLessThanOrEqualTo120Multiplier())) {
          calculationContext.setBundledComorbidityMultiplier(EsrdPricerContext.DEFAULT_MULTIPLIER);

          bundledData.setComorbidityPaymentCode(
              EsrdPricerContext.COMORBIDITY_RETURN_CODE_DEFAULT_10);
        } else {
          calculateComorbidityAdjustment(calculationContext);

          calculationContext.setBundledComorbidityMultiplier(
              calculationContext.getComorbidityMultiplier());
        }
      }
    } else {
      switch (calculationContext.getCwfReturnCode()) {
        case EsrdPricerContext.COMORBIDITY_RETURN_CODE_DEFAULT_10:
          calculationContext.setBundledComorbidityMultiplier(EsrdPricerContext.DEFAULT_MULTIPLIER);

          bundledData.setComorbidityPaymentCode(calculationContext.getCwfReturnCode());

          break;
        case EsrdPricerContext.COMORBIDITY_RETURN_CODE_GASTROENTERITIS_20:
          calculationContext.setBundledComorbidityMultiplier(
              calculationContext.getCaseMixGastrointestinalBleedMultiplier());

          bundledData.setComorbidityPaymentCode(calculationContext.getCwfReturnCode());

          break;
        case EsrdPricerContext.COMORBIDITY_RETURN_CODE_PERICARDITIS_40:
          calculationContext.setBundledComorbidityMultiplier(
              calculationContext.getCaseMixPericarditisMultiplier());

          bundledData.setComorbidityPaymentCode(calculationContext.getCwfReturnCode());

          break;
        default:
          break;
      }
    }
  }

  /**
   * Calculates the comorbidity adjustment.
   *
   * <pre>
   * *****************************************************************
   * **  Calculate Co-morbidities adjustment                       ***
   * *****************************************************************
   *  This logic assumes that the comorbids are randomly assigned   *
   * to the comorbid table.  It will select the highest comorbid for *
   * payment if one is found.  CY 2016 DROPPED MB &amp; MF              *
   * *****************************************************************
   * </pre>
   *
   * <p>Converted from {@code 2100-CALC-COMORBID-ADJUST} in the COBOL code.
   *
   * @param calculationContext the current execution context
   */
  protected void calculateComorbidityAdjustment(EsrdPricerContext calculationContext) {
    final BundledPaymentData bundledData = calculationContext.getBundledData();

    //     MOVE 'N'                          TO IS-HIGH-COMORBID-FOUND.
    //     MOVE 1.000                        TO H-COMORBID-MULTIPLIER.
    calculationContext.setComorbidityMultiplier(EsrdPricerContext.DEFAULT_MULTIPLIER);
    //     MOVE '10'                         TO PPS-2011-COMORBID-PAY.
    bundledData.setComorbidityPaymentCode(EsrdPricerContext.COMORBIDITY_RETURN_CODE_DEFAULT_10);

    //     PERFORM VARYING  SUB  FROM  1 BY 1
    //       UNTIL SUB   >  6   OR   HIGH-COMORBID-FOUND
    // NOTE: the HIGH-COMORBID-FOUND flag is not used in the COBOL of this loop
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
    //         IF COMORBID-DATA (SUB) = 'MA'  THEN
    //           MOVE CM-GI-BLEED            TO H-COMORBID-MULTIPLIER
    //           MOVE "Y"                    TO ACUTE-COMORBID-TRACK
    //           MOVE '20'                   TO PPS-2011-COMORBID-PAY
    //           ELSE
    //             IF COMORBID-DATA (SUB) = 'MC'  THEN
    //                IF CM-PERICARDITIS  >
    //                                      H-COMORBID-MULTIPLIER  THEN
    //                  MOVE CM-PERICARDITIS TO H-COMORBID-MULTIPLIER
    //                  MOVE "Y"             TO ACUTE-COMORBID-TRACK
    //                  MOVE '40'            TO PPS-2011-COMORBID-PAY
    //                END-IF
    //             ELSE
    //               IF COMORBID-DATA (SUB) = 'MD'  THEN
    //                 IF CM-MYELODYSPLASTIC  >
    //                                      H-COMORBID-MULTIPLIER  THEN
    //                   MOVE CM-MYELODYSPLASTIC  TO
    //                                      H-COMORBID-MULTIPLIER
    //                   MOVE "Y"            TO CHRONIC-COMORBID-TRACK
    //                   MOVE '50'           TO PPS-2011-COMORBID-PAY
    //                 END-IF
    //               ELSE
    //                 IF COMORBID-DATA (SUB) = 'ME'  THEN
    //                   IF CM-SICKEL-CELL  >
    //                                      H-COMORBID-MULTIPLIER  THEN
    //                     MOVE CM-SICKEL-CELL  TO
    //                                      H-COMORBID-MULTIPLIER
    //                     MOVE "Y"          TO CHRONIC-COMORBID-TRACK
    //                     MOVE '60'         TO PPS-2011-COMORBID-PAY
    //                   END-IF
    //                 END-IF
    //               END-IF
    //             END-IF
    //         END-IF
    //     END-PERFORM.
    final PayerOnlyConditionCode payerOnlyConditionCode =
        PayerOnlyConditionCode.valueOf(comorbidityCode);
    switch (payerOnlyConditionCode) {
      case MA:
        calculationContext.setComorbidityMultiplier(
            calculationContext.getCaseMixGastrointestinalBleedMultiplier());
        calculationContext.setAcuteComorbidityFound(true);

        bundledData.setComorbidityPaymentCode(payerOnlyConditionCode.getReturnCode());

        break;
      case MC:
        if (BigDecimalUtils.isGreaterThan(
            calculationContext.getCaseMixPericarditisMultiplier(),
            calculationContext.getComorbidityMultiplier())) {
          calculationContext.setComorbidityMultiplier(
              calculationContext.getCaseMixPericarditisMultiplier());

          calculationContext.setAcuteComorbidityFound(true);

          bundledData.setComorbidityPaymentCode(payerOnlyConditionCode.getReturnCode());
        }

        break;
      case MD:
        if (BigDecimalUtils.isGreaterThan(
            calculationContext.getCaseMixMyelodysplasticSyndromeMultiplier(),
            calculationContext.getComorbidityMultiplier())) {
          calculationContext.setComorbidityMultiplier(
              calculationContext.getCaseMixMyelodysplasticSyndromeMultiplier());

          calculationContext.setChronicComorbidityFound(true);

          bundledData.setComorbidityPaymentCode(payerOnlyConditionCode.getReturnCode());
        }

        break;
      case ME:
        if (BigDecimalUtils.isGreaterThan(
            calculationContext.getCaseMixSickleCellMultiplier(),
            calculationContext.getComorbidityMultiplier())) {
          calculationContext.setComorbidityMultiplier(
              calculationContext.getCaseMixSickleCellMultiplier());

          calculationContext.setChronicComorbidityFound(true);

          bundledData.setComorbidityPaymentCode(payerOnlyConditionCode.getReturnCode());
        }

        break;
      default:
        break;
    }
  }
}
