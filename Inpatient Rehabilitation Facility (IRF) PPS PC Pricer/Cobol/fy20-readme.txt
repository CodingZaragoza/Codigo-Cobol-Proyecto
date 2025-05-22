************************************************************************
*                                                                      *
*                           README                                     *
*                                                                      *
* DATASET NAME:        MU00.@BFN2699.IRNDM200                          *
* PRICER:              INPATIENT REHAB PAYMENT SYSTEM (IRF)            *
* VERSION:             2020.0                                          *
* CR#:                 11345                                           *
*                                                                      *
* IMPLEMENTATION DATE: 10/01/2019                                      *
* BETA/PRODUCTION:     PRODUCTION                                      *
* RELEASE DATE:        08/06/2019                                      *
*                                                                      *
************************************************************************

DESCRIPTION:

******************************************************************
* CHANGES FOR FY2020 - EFFECTIVE 10/01/2019                      *
*----------------------------------------------------------------*
* UPDATED CMG-TABLE - ADDED 8 ADDITIONAL CMG'S                   *
* UPDATED 0100-INITIAL-ROUTINE                                   *
*                                                                *
*   MOVE .72700 TO PPS-NAT-LABOR-PCT.                            *
*   MOVE .27300 TO PPS-NAT-NONLABOR-PCT.                         *
*   MOVE  9300  TO PPS-NAT-THRESHOLD-ADJ.                        *
*   IF P-NEW-CBSA-HOSP-QUAL-IND IS EQUAL TO '1'                  *
*      MOVE 16489  TO PPS-BDGT-NEUT-CONV-AMT                     *
*   ELSE                                                         *
*      MOVE 16167  TO PPS-BDGT-NEUT-CONV-AMT                     *
*   END-IF.                                                      *
*                                                                *
*   NO CHANGE TO LOW INCOME PATIENT (LIP) ADJ = 0.3177           *
*   NO CHANGE TO TEACHING ADJ = 1.0163                           *
*   NO CHANGE TO RURAL-ADJUSTMENT                                *
*   IF W-NEW-CBSA (1:3) = '   '                                  *
*     MOVE 1.1490 TO PPS-RURAL-ADJUSTMENT                        *
*   ELSE                                                         *
*     MOVE 1.0000 TO PPS-RURAL-ADJUSTMENT.                       *
*                                                                *
*   FOR TRANSFER CASES, THE STANDARD PAYMENT IS NOW COMPUTED     *
*   USING THE PER DIEM - 3000-CALC-PAYMENT                       *
*                                                                *
******************************************************************

RELEASE CONTENTS:

MU00.@BFN2699.IRNDM200:

README   - DESCRIPTION OF RELEASE
MANIFEST - GENERAL RELEASE INFORMATION
IRCBS200 - 2020 CORE BASED STATISTICAL AREA
IRCAL200 - NEW CALC PROGRAM FOR V200
IRDRV200 - NEW DRIVER PROGRAM FOR V200

