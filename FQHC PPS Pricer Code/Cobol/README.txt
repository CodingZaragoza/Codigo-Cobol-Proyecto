      *********************************************
      *   07/11/2019  RELEASE - V20192            *
      *            Production                     *
      *********************************************
NOTE TO: Shared System Coordinators, MEDICARE Coordinators

SUBJECT: Federally Qualified Health Center (FQHC) Prospective Payment
         System (PPS) Pricer Release, Version 20192
(July 11, 2019)  --ACTION-- Refer to CMS change request CR# 11203

This FQHC Pricer release is designed for implementation in conjunction
with the IOCE lines in the shared claims payment systems on or
about 01Jul2019.


This release includes the following updates:
------------------------------------------------------------------------
  - Version changed from 2019.1 to 2019.2
  - Updated this README and the MANIFEST members for this release.
  - Added records for VI Carrier/Locality 09202/50 for CYs 2017 - 2019
    in members BASEGAF and COPYGAF.
  - The following changes were retained from version 2019.1:
    - Updated 2019 BASEGFTF & COPYGFTF rate to $405.00
************************************************************************


A partitioned data set of the Version 20192 code
(MU00.@BF12390.FQHC.V20192.COBOL) is at the CMS Data Center for users
to obtain by Connect:Direct (Network DataMover).

The 12 members below comprise the FQHC Pricer:

1  source program - FQHCCAL.

4  copybooks  - COPYADD,COPYBASE,COPYGAF,COPYGFTF

4  databooks  - BASEADD,BASEBASE,BASEGAF,BASEGFTF

3  text files - copy of this Release Memorandum,
                a list of the return codes (expanded by user notes),
                a manifest listing files associated with this release
              - README,RTNCODES,MANIFEST


If you have any questions concerning this release or any other FQHC
Pricer-related issues, please call Tracey Mackey - (410) 786-5736.





                              Crystal Graham & Tamara Howard
                              Computer Specialist/Systems Analyst
                              Division of Data Systems
                              CMS/CMM/PBG/DDS



                                   Page 1 of 2
FQHC Pricer Release Notes:

Below are brief descriptions of the FQHC Pricer modules in
the system plus a copy of the Linkage Section and
the CALL statement needed to access the module.

BASEADD  - human-readable data from which the COPYADD copybook is built

BASEBASE - human-readable data from which the COPYBASE copybook is built

BASEGAF  - human-readable data from which the COPYGAF copybook is built

BASEGFTF - human-readable data from which the COPYGFTF copybook is built

COPYADD  - copybook of add-on rates by effective date for FQHCCAL

COPYBASE - copybook of base rates by effective date for FQHCCAL

COPYGAF  - copybook of GAF rates by carrier-locality for FQHCCAL

COPYGFTF - copybook of GFTF rates by effective date for FQHCCAL

FQHCCAL  - source code for FQHC pricing subroutine

MANIFEST - list of files associated with the release

README   - copy of this memorandum

RTNCODES - list of FQHC Pricer claim and line level return codes




This is the LINKAGE SECTION defined in FQHCCAL.


       LINKAGE SECTION.

      ***************************************************************
      ***************************************************************
      ***                                                         ***
      **         LINKAGE SECTION INPUT AND OUTPUT RECORDS          **
      ***                                                         ***
      ***************************************************************
      ***************************************************************
      *                                                             *
      *   1) INPUT RECORD FROM CONTRACTOR                           *
      *   2) OUTPUT RECORD TO CONTRACTOR                            *
      *                                                             *
      ***************************************************************


      ***************************************************************
      *  INPUT RECORD FROM THE CONTRACTOR                           *
      *-------------------------------------------------------------*
      * BELOW ARE THE VARIABLES THAT WILL BE PASSED TO PRICER FROM  *
      * THE CONTRACTOR (INCLUDES ITEMS FROM THE CLAIM AND IOCE;     *
      * CLAIM LEVEL AND LINE LEVEL VARIABLES)                       *
      ***************************************************************
       01  INPUT-RECORD.
           05  I-CLAIM-LEVEL-INPUT.
               10  I-PROVIDER-NUMBER          PIC X(06).
               10  I-CARRIER-LOCALITY.
                   15  I-CARRIER              PIC X(05).
                   15  I-LOCALITY             PIC X(02).
               10  I-MA-PLAN-AMT              PIC 9(09)V99.
               10  I-SRVC-FROM-DATE           PIC 9(08).
               10  I-SRVC-THRU-DATE           PIC 9(08).
               10  I-SRVC-LINE-CNT            PIC 9(03).
           05  I-SERVICE-LINE OCCURS 450 TIMES
                     DEPENDING ON I-SRVC-LINE-CNT.
               10  I-LINE-LEVEL-INPUT.
                   15  I-HCPCS                PIC X(05).
                   15  I-MODIFIERS.
                       20  I-MODIFIER-1       PIC X(02).
                       20  I-MODIFIER-2       PIC X(02).
                       20  I-MODIFIER-3       PIC X(02).
                       20  I-MODIFIER-4       PIC X(02).
                       20  I-MODIFIER-5       PIC X(02).
                   15  I-LINE-SRVC-DATE       PIC 9(08).
                   15  I-REVENUE-CODE         PIC X(04).
                   15  I-TOT-UNITS            PIC 9(09).
                   15  I-COV-UNITS            PIC 9(09).
                   15  I-COV-CHARGES          PIC 9(09)V99.
               10  I-IOCE-LINE-FLAGS.
                   15  I-SRVC-IND             PIC X(02).
                   15  I-PYMT-IND             PIC X(02).
                   15  I-DISCOUNT-FACT        PIC 9(01).
                   15  I-LITEM-DENY-REJ-FLAG  PIC X(01).
                   15  I-PKG-FLAG             PIC X(01).
                   15  I-PYMT-ADJ-FLAG        PIC X(02).
                   15  I-PYMT-METHOD-FLAG     PIC X(01).
                   15  I-LITEM-ACT-FLAG       PIC X(01).
                   15  I-COMP-ADJ-FLAG        PIC X(02).



      ***************************************************************
      *  OUTPUT RECORD TO THE CONTRACTOR                            *
      *-------------------------------------------------------------*
      *   BELOW ARE THE VARIABLES THAT WILL BE PASSED BACK TO THE   *
      *   CONTRACTOR ASSOCIATED WITH THE BILL BEING PROCESSED       *
      ***************************************************************
       01  OUTPUT-RECORD.
           05  O-CLAIM-LEVEL-OUTPUT.
               10  O-CALC-VERS                PIC X(07).
               10  O-TOT-CLM-PYMT             PIC 9(09)V99.
               10  O-TOT-CLM-COIN             PIC 9(09)V99.
               10  O-GEO-ADJ-FACT             PIC 9(01)V9(04).
               10  O-CLM-RETURN-CODE          PIC 9(02).
           05  O-LINE-LEVEL-OUTPUT OCCURS 450 TIMES
                   DEPENDING ON I-SRVC-LINE-CNT.
               10  O-LITEM-PYMT               PIC 9(09)V99.
               10  O-LITEM-ADD-ON-PYMT        PIC 9(09)V99.
               10  O-LITEM-COIN               PIC 9(09)V99.
               10  O-LITEM-RETURN-CODE        PIC 9(02).



   ***************************************************************
   *    PROCESSING:                                              *
   *        Below is the CALL STATEMENT and variables listed     *
   *             required by the FQHCCAL module.                 *
   ***************************************************************

       PROCEDURE DIVISION  USING INPUT-RECORD
                                 OUTPUT-RECORD.


