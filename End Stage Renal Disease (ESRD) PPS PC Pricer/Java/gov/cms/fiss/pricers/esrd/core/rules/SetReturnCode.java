package gov.cms.fiss.pricers.esrd.core.rules;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import gov.cms.fiss.pricers.esrd.core.codes.ReturnCode;

public class SetReturnCode
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  /**
   * Determines the appropriate return code based on the state of the claim.
   *
   * <pre>
   * *****************************************************************
   * **  Set the return code                                       ***
   * *****************************************************************
   *   The following 'table' helps in understanding and in making   *
   * changes to the rather large and complex "IF" statement that    *
   * follows.  This 'table' just reorders and rewords the comments  *
   * contained in the working storage area concerning the paid      *
   * return-codes.                                                  *
   *                                                                *
   *  17 = pediatric, outlier, training                             *
   *  16 = pediatric, outlier                                       *
   *  15 = pediatric, training                                      *
   *  14 = pediatric                                                *
   *                                                                *
   *  24 = outlier, low volume, training, chronic comorbid          *
   *  19 = outlier, low volume, training, acute comorbid            *
   *  29 = outlier, low volume, training                            *
   *  23 = outlier, low volume, chronic comorbid                    *
   *  18 = outlier, low volume, acute comorbid                      *
   *  30 = outlier, low volume, onset                               *
   *  28 = outlier, low volume                                      *
   *  34 = outlier, training, chronic comorbid                      *
   *  35 = outlier, training, acute comorbid                        *
   *  33 = outlier, training                                        *
   *  07 = outlier, chronic comorbid                                *
   *  06 = outlier, acute comorbid                                  *
   *  09 = outlier, onset                                           *
   *  03 = outlier                                                  *
   *                                                                *
   *  26 = low volume, training, chronic comorbid                   *
   *  21 = low volume, training, acute comorbid                     *
   *  12 = low volume, training                                     *
   *  25 = low volume, chronic comorbid                             *
   *  20 = low volume, acute comorbid                               *
   *  32 = low volume, onset                                        *
   *  10 = low volume                                               *
   *                                                                *
   *  27 = training, chronic comorbid                               *
   *  22 = training, acute comorbid                                 *
   *  11 = training                                                 *
   *                                                                *
   *  08 = onset                                                    *
   *  04 = acute comorbid                                           *
   *  05 = chronic comorbid                                         *
   *  31 = low BMI                                                  *
   *  02 = no adjustments                                           *
   *                                                                *
   *  13 = w/multiple adjustments....reserved for future use        *
   * *****************************************************************
   * </pre>
   *
   * <p>Converted from {@code 9000-SET-RETURN-CODE} in the COBOL code.
   */
  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    //     IF PEDIATRIC-TRACK                       = "Y"  THEN
    //           END-IF
    //     ELSE
    //        IF OUTLIER-TRACK                      = "Y"  THEN
    //        END-IF
    //        ELSE
    //           IF LOW-VOLUME-TRACK                = "Y"
    //     END-IF.
    //            ELSE
    //               IF TRAINING-TRACK               = "Y"  THEN
    //     END-IF.
    //     END-IF.
    if (calculationContext.isPediatricClaim()) {
      applyPediatricReturnCode(calculationContext);

      return;
    }

    if (calculationContext.isOutlierClaim()) {
      applyOutlierReturnCode(calculationContext);

      return;
    }

    if (calculationContext.isLowVolumeClaim()) {
      applyLowVolumeReturnCode(calculationContext);

      return;
    }

    if (calculationContext.isTrainingClaim()) {
      applyTrainingReturnCode(calculationContext);

      return;
    }

    applyOtherReturnCode(calculationContext);
  }

  private void applyPediatricReturnCode(EsrdPricerContext calculationContext) {
    //        IF OUTLIER-TRACK                      = "Y"  THEN
    //           IF TRAINING-TRACK                  = "Y"  THEN
    //              MOVE 17                  TO PPS-RTC
    //           ELSE
    //              MOVE 16                  TO PPS-RTC
    //           END-IF
    //        ELSE
    //           IF TRAINING-TRACK                  = "Y"  THEN
    //              MOVE 15                  TO PPS-RTC
    //           ELSE
    //              MOVE 14                  TO PPS-RTC
    //           END-IF
    //        END-IF
    if (calculationContext.isOutlierClaim()) {
      if (calculationContext.isTrainingClaim()) {
        calculationContext.applyReturnCode(ReturnCode.OUTLIER_PEDIATRIC_TRAINING_PAYMENT_17);
      } else {
        calculationContext.applyReturnCode(ReturnCode.OUTLIER_PEDIATRIC_PAYMENT_16);
      }
    } else if (calculationContext.isTrainingClaim()) {
      calculationContext.applyReturnCode(ReturnCode.PEDIATRIC_PAYMENT_TRAINING_15);
    } else {
      calculationContext.applyReturnCode(ReturnCode.PEDIATRIC_PAYMENT_14);
    }
  }

  private void applyOutlierReturnCode(EsrdPricerContext calculationContext) {
    //            IF LOW-VOLUME-TRACK                = "Y"  THEN
    //            ELSE
    //               IF TRAINING-TRACK               = "Y"  THEN
    //                  IF CHRONIC-COMORBID-TRACK    = "Y"  THEN
    //                     MOVE 34            TO PPS-RTC
    //                  ELSE
    //                     IF ACUTE-COMORBID-TRACK   = "Y"  THEN
    //                        MOVE 35         TO PPS-RTC
    //                     ELSE
    //                        MOVE 33         TO PPS-RTC
    //                     END-IF
    //                  END-IF
    //               ELSE
    //                  IF CHRONIC-COMORBID-TRACK    = "Y"  THEN
    //                     MOVE 07            TO PPS-RTC
    //                  ELSE
    //                     IF ACUTE-COMORBID-TRACK   = "Y"  THEN
    //                        MOVE 06         TO PPS-RTC
    //                     ELSE
    //                        IF ONSET-TRACK         = "Y"  THEN
    //                           MOVE 09      TO PPS-RTC
    //                        ELSE
    //                           MOVE 03      TO PPS-RTC
    //                        END-IF
    //                     END-IF
    //                  END-IF
    //               END-IF
    if (calculationContext.isLowVolumeClaim()) {
      applyLowVolumeOutlierReturnCode(calculationContext);
    } else if (calculationContext.isTrainingClaim()) {
      if (calculationContext.isChronicComorbidityFound()) {
        calculationContext.applyReturnCode(
            ReturnCode.OUTLIER_TRAINING_PAYMENT_WITH_CHRONIC_COMORBIDITY_34);
      } else if (calculationContext.isAcuteComorbidityFound()) {
        calculationContext.applyReturnCode(
            ReturnCode.OUTLIER_TRAINING_PAYMENT_WITH_ACUTE_COMORBIDITY_35);
      } else {
        calculationContext.applyReturnCode(ReturnCode.OUTLIER_TRAINING_PAYMENT_33);
      }
    } else if (calculationContext.isChronicComorbidityFound()) {
      calculationContext.applyReturnCode(ReturnCode.OUTLIER_PAYMENT_WITH_CHRONIC_COMORBIDITY_07);
    } else if (calculationContext.isAcuteComorbidityFound()) {
      calculationContext.applyReturnCode(ReturnCode.OUTLIER_PAYMENT_WITH_ACUTE_COMORBIDITY_06);
    } else if (calculationContext.isOnsetRecent()) {
      calculationContext.applyReturnCode(ReturnCode.OUTLIER_PAYMENT_WITH_ONSET_09);
    } else {
      calculationContext.applyReturnCode(ReturnCode.OUTLIER_PAYMENT_03);
    }
  }

  private void applyLowVolumeOutlierReturnCode(EsrdPricerContext calculationContext) {
    //               IF TRAINING-TRACK               = "Y"  THEN
    //                  IF CHRONIC-COMORBID-TRACK    = "Y"  THEN
    //                     MOVE 24            TO PPS-RTC
    //                  ELSE
    //                     IF ACUTE-COMORBID-TRACK   = "Y"  THEN
    //                        MOVE 19         TO PPS-RTC
    //                     ELSE
    //                        MOVE 29         TO PPS-RTC
    //                     END-IF
    //                  END-IF
    //               ELSE
    //                  IF CHRONIC-COMORBID-TRACK    = "Y"  THEN
    //                     MOVE 23            TO PPS-RTC
    //                  ELSE
    //                     IF ACUTE-COMORBID-TRACK   = "Y"  THEN
    //                        MOVE 18         TO PPS-RTC
    //                     ELSE
    //                        IF ONSET-TRACK         = "Y"  THEN
    //                           MOVE 30      TO PPS-RTC
    //                        ELSE
    //                           MOVE 28      TO PPS-RTC
    //                        END-IF
    //                     END-IF
    //                  END-IF
    //               END-IF
    if (calculationContext.isTrainingClaim()) {
      if (calculationContext.isChronicComorbidityFound()) {
        calculationContext.applyReturnCode(
            ReturnCode.LOW_VOLUME_OUTLIER_TRAINING_PAYMENT_WITH_CHRONIC_COMORBIDITY_24);
      } else if (calculationContext.isAcuteComorbidityFound()) {
        calculationContext.applyReturnCode(
            ReturnCode.LOW_VOLUME_OUTLIER_TRAINING_PAYMENT_WITH_ACUTE_COMORBIDITY_19);
      } else {
        calculationContext.applyReturnCode(ReturnCode.LOW_VOLUME_OUTLIER_TRAINING_PAYMENT_29);
      }
    } else if (calculationContext.isChronicComorbidityFound()) {
      calculationContext.applyReturnCode(
          ReturnCode.LOW_VOLUME_OUTLIER_PAYMENT_WITH_CHRONIC_COMORBIDITY_23);
    } else if (calculationContext.isAcuteComorbidityFound()) {
      calculationContext.applyReturnCode(
          ReturnCode.LOW_VOLUME_OUTLIER_PAYMENT_WITH_ACUTE_COMORBIDITY_18);
    } else if (calculationContext.isOnsetRecent()) {
      calculationContext.applyReturnCode(ReturnCode.LOW_VOLUME_OUTLIER_PAYMENT_WITH_ONSET_30);
    } else {
      calculationContext.applyReturnCode(ReturnCode.LOW_VOLUME_OUTLIER_PAYMENT_28);
    }
  }

  private void applyLowVolumeReturnCode(EsrdPricerContext calculationContext) {
    //               IF TRAINING-TRACK               = "Y"  THEN
    //                  IF CHRONIC-COMORBID-TRACK    = "Y"  THEN
    //                     MOVE 26            TO PPS-RTC
    //                  ELSE
    //                     IF ACUTE-COMORBID-TRACK   = "Y"  THEN
    //                        MOVE 21         TO PPS-RTC
    //                     ELSE
    //                        MOVE 12         TO PPS-RTC
    //                     END-IF
    //                  END-IF
    //               ELSE
    //                  IF CHRONIC-COMORBID-TRACK    = "Y"  THEN
    //                     MOVE 25            TO PPS-RTC
    //                  ELSE
    //                     IF ACUTE-COMORBID-TRACK   = "Y"  THEN
    //                        MOVE 20         TO PPS-RTC
    //                     ELSE
    //                        IF ONSET-TRACK         = "Y"  THEN
    //                           MOVE 32      TO PPS-RTC
    //                        ELSE
    //                           MOVE 10      TO PPS-RTC
    //                        END-IF
    //                     END-IF
    //                  END-IF
    if (calculationContext.isTrainingClaim()) {
      if (calculationContext.isChronicComorbidityFound()) {
        calculationContext.applyReturnCode(
            ReturnCode.LOW_VOLUME_TRAINING_PAYMENT_WITH_CHRONIC_COMORBIDITY_26);
      } else if (calculationContext.isAcuteComorbidityFound()) {
        calculationContext.applyReturnCode(
            ReturnCode.LOW_VOLUME_TRAINING_PAYMENT_WITH_ACUTE_COMORBIDITY_21);
      } else {
        calculationContext.applyReturnCode(ReturnCode.LOW_VOLUME_TRAINING_PAYMENT_12);
      }
    } else if (calculationContext.isChronicComorbidityFound()) {
      calculationContext.applyReturnCode(ReturnCode.LOW_VOLUME_PAYMENT_WITH_CHRONIC_COMORBIDITY_25);
    } else if (calculationContext.isAcuteComorbidityFound()) {
      calculationContext.applyReturnCode(ReturnCode.LOW_VOLUME_PAYMENT_WITH_ACUTE_COMORBIDITY_20);
    } else if (calculationContext.isOnsetRecent()) {
      calculationContext.applyReturnCode(ReturnCode.LOW_VOLUME_PAYMENT_WITH_ONSET_32);
    } else {
      calculationContext.applyReturnCode(ReturnCode.LOW_VOLUME_PAYMENT_10);
    }
  }

  private void applyTrainingReturnCode(EsrdPricerContext calculationContext) {
    //                  IF CHRONIC-COMORBID-TRACK    = "Y"  THEN
    //                     MOVE 27            TO PPS-RTC
    //                  ELSE
    //                     IF ACUTE-COMORBID-TRACK   = "Y"  THEN
    //                        MOVE 22         TO PPS-RTC
    //                     ELSE
    //                        MOVE 11         TO PPS-RTC
    //                     END-IF
    if (calculationContext.isChronicComorbidityFound()) {
      calculationContext.applyReturnCode(ReturnCode.TRAINING_PAYMENT_WITH_CHRONIC_COMORBIDITY_27);
    } else if (calculationContext.isAcuteComorbidityFound()) {
      calculationContext.applyReturnCode(ReturnCode.TRAINING_PAYMENT_WITH_ACUTE_COMORBIDITY_22);
    } else {
      calculationContext.applyReturnCode(ReturnCode.TRAINING_PAYMENT_11);
    }
  }

  private void applyOtherReturnCode(EsrdPricerContext calculationContext) {
    //               ELSE
    //                  IF ONSET-TRACK               = "Y"  THEN
    //                     MOVE 08            TO PPS-RTC
    //                  ELSE
    //                     IF ACUTE-COMORBID-TRACK   = "Y"  THEN
    //                        MOVE 04         TO PPS-RTC
    //                     ELSE
    //                        IF CHRONIC-COMORBID-TRACK = "Y"  THEN
    //                           MOVE 05      TO PPS-RTC
    //                        ELSE
    //                           IF LOW-BMI-TRACK = "Y"  THEN
    //                              MOVE 31 TO PPS-RTC
    //                           ELSE
    //                              MOVE 02 TO PPS-RTC
    //                           END-IF
    //                        END-IF
    //                     END-IF
    //                  END-IF
    if (calculationContext.isOnsetRecent()) {
      calculationContext.applyReturnCode(ReturnCode.PAYMENT_WITH_ONSET_08);
    } else if (calculationContext.isAcuteComorbidityFound()) {
      calculationContext.applyReturnCode(ReturnCode.PAYMENT_WITH_ACUTE_COMORBIDITY_04);
    } else if (calculationContext.isChronicComorbidityFound()) {
      calculationContext.applyReturnCode(ReturnCode.PAYMENT_WITH_CHRONIC_COMORBIDITY_05);
    } else if (calculationContext.isLowBmiClaim()) {
      calculationContext.applyReturnCode(ReturnCode.PAYMENT_WITH_LOW_BMI_31);
    } else {
      calculationContext.applyReturnCode(ReturnCode.PAYMENT_NO_ADJUSTMENTS_02);
    }
  }
}
