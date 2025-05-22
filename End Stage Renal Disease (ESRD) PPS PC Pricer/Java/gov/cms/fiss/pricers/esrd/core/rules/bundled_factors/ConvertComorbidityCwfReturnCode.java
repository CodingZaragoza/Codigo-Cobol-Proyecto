package gov.cms.fiss.pricers.esrd.core.rules.bundled_factors;

import gov.cms.fiss.pricers.common.application.rules.CalculationRule;
import gov.cms.fiss.pricers.esrd.api.v2.ComorbidityData;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingRequest;
import gov.cms.fiss.pricers.esrd.api.v2.EsrdClaimPricingResponse;
import gov.cms.fiss.pricers.esrd.core.EsrdPricerContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.StringUtils;

/**
 * Converts the return code from the comorbidity common working file to a corresponding comorbidity
 * code. As the comorbidity structure is not part of the API response, the copying described in the
 * COBOL-sourced comments was not implemented.
 *
 * <pre>
 * Since pricer has to pay a comorbid condition according to the
 * return code that CWF passes back, it is cleaner if the pricer
 * sets aside whatever comorbid data exists on the line-item when
 * it comes into the pricer and then transfers the CWF code to
 * the appropriate place in the comorbid data.  This avoids
 * making convoluted changes in the other parts of the program
 * which has to look at both original comorbid data AND CWF return
 * codes to handle comorbids.  Near the end of the program where
 * variables are transferred to the output, the original comorbid
 * data is put back into its original place as though nothing
 * occurred.
 * </pre>
 *
 * <p>Converted from {@code 2000-CALCULATE-BUNDLED-FACTORS} (continued) in the COBOL code.
 *
 * @since 2020
 */
public class ConvertComorbidityCwfReturnCode
    implements CalculationRule<
        EsrdClaimPricingRequest, EsrdClaimPricingResponse, EsrdPricerContext> {

  @Override
  public void calculate(EsrdPricerContext calculationContext) {
    final ComorbidityData comorbidities = calculationContext.getComorbidities();

    // *CY2016 DROPPED MB & MF
    //     IF COMORBID-CWF-RETURN-CODE = SPACES  THEN
    //        NEXT SENTENCE
    //     ELSE
    //        MOVE 'Y'                       TO MOVED-CORMORBIDS
    //        MOVE COMORBID-DATA (1)         TO H-COMORBID-DATA (1)
    //        MOVE COMORBID-DATA (2)         TO H-COMORBID-DATA (2)
    //        MOVE COMORBID-DATA (3)         TO H-COMORBID-DATA (3)
    //        MOVE COMORBID-DATA (4)         TO H-COMORBID-DATA (4)
    //        MOVE COMORBID-DATA (5)         TO H-COMORBID-DATA (5)
    //        MOVE COMORBID-DATA (6)         TO H-COMORBID-DATA (6)
    //        MOVE COMORBID-CWF-RETURN-CODE  TO H-COMORBID-CWF-CODE
    //        IF COMORBID-CWF-RETURN-CODE = '10'  THEN
    //           MOVE SPACES                 TO COMORBID-DATA (1)
    //                                          COMORBID-DATA (2)
    //                                          COMORBID-DATA (3)
    //                                          COMORBID-DATA (4)
    //                                          COMORBID-DATA (5)
    //                                          COMORBID-DATA (6)
    //                                          COMORBID-CWF-RETURN-CODE
    //        ELSE
    //           IF COMORBID-CWF-RETURN-CODE = '20'  THEN
    //              MOVE 'MA'                TO COMORBID-DATA (1)
    //              MOVE SPACES              TO COMORBID-DATA (2)
    //                                          COMORBID-DATA (3)
    //                                          COMORBID-DATA (4)
    //                                          COMORBID-DATA (5)
    //                                          COMORBID-DATA (6)
    //                                          COMORBID-CWF-RETURN-CODE
    //           ELSE
    //                 IF COMORBID-CWF-RETURN-CODE = '40'  THEN
    //                    MOVE SPACES        TO COMORBID-DATA (1)
    //                    MOVE SPACES        TO COMORBID-DATA (2)
    //                    MOVE 'MC'          TO COMORBID-DATA (3)
    //                    MOVE SPACES        TO COMORBID-DATA (4)
    //                    MOVE SPACES        TO COMORBID-DATA (5)
    //                    MOVE SPACES        TO COMORBID-DATA (6)
    //                                          COMORBID-CWF-RETURN-CODE
    //                 ELSE
    //                    IF COMORBID-CWF-RETURN-CODE = '50'  THEN
    //                       MOVE SPACES     TO COMORBID-DATA (1)
    //                       MOVE SPACES     TO COMORBID-DATA (2)
    //                       MOVE SPACES     TO COMORBID-DATA (3)
    //                       MOVE 'MD'       TO COMORBID-DATA (4)
    //                       MOVE SPACES     TO COMORBID-DATA (5)
    //                       MOVE SPACES     TO COMORBID-DATA (6)
    //                                          COMORBID-CWF-RETURN-CODE
    //                    ELSE
    //                       IF COMORBID-CWF-RETURN-CODE = '60'  THEN
    //                          MOVE SPACES  TO COMORBID-DATA (1)
    //                          MOVE SPACES  TO COMORBID-DATA (2)
    //                          MOVE SPACES  TO COMORBID-DATA (3)
    //                          MOVE SPACES  TO COMORBID-DATA (4)
    //                          MOVE 'ME'    TO COMORBID-DATA (5)
    //                          MOVE SPACES  TO COMORBID-DATA (6)
    //                                          COMORBID-CWF-RETURN-CODE
    //                       END-IF
    //                    END-IF
    //                 END-IF
    //           END-IF
    //        END-IF
    //     END-IF.
    if (StringUtils.isEmpty(calculationContext.getCwfReturnCode())) {
      calculationContext.setComorbidityCodes(
          Optional.ofNullable(comorbidities.getComorbidityCodes()).orElseGet(ArrayList::new));
    } else {
      switch (calculationContext.getCwfReturnCode()) {
        case "10":
          calculationContext.setCwfReturnCode(null);

          break;
        case "20":
          calculationContext.setComorbidityCodes(List.of("MA"));
          calculationContext.setCwfReturnCode(null);

          break;
        case "40":
          calculationContext.setComorbidityCodes(List.of("MC"));
          calculationContext.setCwfReturnCode(null);

          break;
        case "50":
          calculationContext.setComorbidityCodes(List.of("MD"));
          calculationContext.setCwfReturnCode(null);

          break;
        case "60":
          calculationContext.setComorbidityCodes(List.of("ME"));
          calculationContext.setCwfReturnCode(null);

          break;
        default:
          break;
      }
    }
  }
}
