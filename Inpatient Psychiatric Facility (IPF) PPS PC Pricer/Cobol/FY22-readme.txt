************************************************************************
*                                                                      *
*                           README                                     *
*                                                                      *
* DATASET NAME:        MU00.@BFN2699.IPNDM220                          *
* PRICER:              INPATIENT PSYCH PAYMENT SYSTEM (IPF)            *
* VERSION:             2022.0                                          *
* CR#:                 12417                                           *
*                                                                      *
* IMPLEMENTATION DATE: 10/01/2021                                      *
* BETA/PRODUCTION:     PRODUCTION                                      *
* RELEASE DATE:        08/13/2021                                      *
*                                                                      *
************************************************************************

DESCRIPTION:

******************************************************************
*  CHANGES/UPDATES FOR FY2022                                    *
******************************************************************
*  -- NO RURAL ADJUSTMENT FACTOR CHANGES                         *
*  -- NO TEACHING ADJUSTMENT FACTOR CHANGES                      *
*  -- NO DRG TABLE CHANGES                                       *
*  -- NO DAY ADJUSTMENT CHANGES                                  *
*  -- NO AGE ADJUSTMENT CHANGES                                  *
*  -- NO COMORBIDITY ADJUSTMENT FACTOR CHANGES                   *
*  -- NO CODE FIRST TABLE UPDATE                                 *
*  -- CREATED COMOR220 COPYBOOK                                  *
*  -- UPDATED OUTLIER CALCULATION WHEN BILL-PRIOR-DAYS > 0       *
*  -- UPDATED RATES                                              *
*    IF P-NEW-CBSA-HOSP-QUAL-IND IS EQUAL TO '1'                 *
*       MOVE 0832.94  TO IPF-BUDGNUT-RATE-AMT                    *
*       MOVE 0358.60  TO IPF-ECT-RATE-AMT                        *
*    ELSE                                                        *
*       MOVE 816.61   TO IPF-BUDGNUT-RATE-AMT                    *
*       MOVE 351.57   TO IPF-ECT-RATE-AMT                        *
*    END-IF.                                                     *
*                                                                *
*    MOVE 14470.00 TO IPF-OUTL-THRES-AMT.                        *
*    MOVE 0.77200  TO IPF-LABOR-SHARE.                           *
*    MOVE 0.22800  TO IPF-NLABOR-SHARE.                          *
*                                                                *
******************************************************************

RELEASE CONTENTS:

MU00.@BFN2699.IPNDM220:

README   - DESCRIPTION OF RELEASE
MANIFEST - GENERAL RELEASE INFORMATION
CBSAV220 - 2022 CORE BASED STATISTICAL AREA
IPCAL220 - NEW CALC PROGRAM FOR FY2022
IPDRV220 - NEW DRIVER PROGRAM FOR FY2022
COMOR220 - COMORBIDITY TABLES FOR FY2022
