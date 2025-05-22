      ****************************************************************
      * COPYBOOK: COMOR220                                           *
      * 19 COMORBIDITY TABLES FOR USE IN INPATIENT PSYCH PPS PRICER  *
      * CATEGORIES 1-6, 6P, AND 7-17                                 *
      * EFFECTIVE: 10/01/2021                                        *
      ****************************************************************
      * UPDATED CAT1  - DEVELOPMENTAL DISABILITIES                   *
      * UPDATED CAT6  - ONCOLOGY TREATMENT - DIAGNOSIS CODES         *
      * UPDATED CAT11 - POISONING                                    *
      ****************************************************************

      ****************************************************************
      * DEVELOPMENTAL DISABILITIES                                   *
      ****************************************************************
       01 CAT1-TABLE.
           05  CAT1-TABLE-DATA.
               10  FILLER           PIC X(7) VALUE 'F70    '.
               10  FILLER           PIC X(7) VALUE 'F71    '.
               10  FILLER           PIC X(7) VALUE 'F72    '.
               10  FILLER           PIC X(7) VALUE 'F73    '.
               10  FILLER           PIC X(7) VALUE 'F78A1  '.
               10  FILLER           PIC X(7) VALUE 'F78A9  '.
               10  FILLER           PIC X(7) VALUE 'F79    '.
           05  FILLER     REDEFINES CAT1-TABLE-DATA.
               10  CAT1-DATA        OCCURS  7 TIMES
                                    ASCENDING KEY IS CAT1-CODE
                                    INDEXED BY IX-CAT1.
                   15  CAT1-CODE    PIC X(7).

      ****************************************************************
      * COAGULATION FACTOR DEFICITS                                  *
      ****************************************************************
       01 CAT2-TABLE.
           05  CAT2-TABLE-DATA.
               10  FILLER           PIC X(7) VALUE 'D66    '.
               10  FILLER           PIC X(7) VALUE 'D67    '.
               10  FILLER           PIC X(7) VALUE 'D680   '.
               10  FILLER           PIC X(7) VALUE 'D681   '.
               10  FILLER           PIC X(7) VALUE 'D682   '.
           05  FILLER     REDEFINES CAT2-TABLE-DATA.
               10  CAT2-DATA        OCCURS  5 TIMES
                                    ASCENDING KEY IS CAT2-CODE
                                    INDEXED BY IX-CAT2.
                   15  CAT2-CODE    PIC X(7).

      ****************************************************************
      * TRACHEOSTOMY                                                 *
      ****************************************************************
       01 CAT3-TABLE.
           05  CAT3-TABLE-DATA.
               10  FILLER           PIC X(7) VALUE 'J9500  '.
               10  FILLER           PIC X(7) VALUE 'J9501  '.
               10  FILLER           PIC X(7) VALUE 'J9502  '.
               10  FILLER           PIC X(7) VALUE 'J9503  '.
               10  FILLER           PIC X(7) VALUE 'J9504  '.
               10  FILLER           PIC X(7) VALUE 'J9509  '.
               10  FILLER           PIC X(7) VALUE 'Z930   '.
           05  FILLER     REDEFINES CAT3-TABLE-DATA.
               10  CAT3-DATA        OCCURS 7 TIMES
                                    ASCENDING KEY IS CAT3-CODE
                                    INDEXED BY IX-CAT3.
                   15  CAT3-CODE    PIC X(7).

      ****************************************************************
      * RENAL FAILURE, ACUTE                                         *
      ****************************************************************
       01 CAT4-TABLE.
           05  CAT4-TABLE-DATA.
               10  FILLER           PIC X(7) VALUE 'N170   '.
               10  FILLER           PIC X(7) VALUE 'N171   '.
               10  FILLER           PIC X(7) VALUE 'N172   '.
               10  FILLER           PIC X(7) VALUE 'N178   '.
               10  FILLER           PIC X(7) VALUE 'N179   '.
               10  FILLER           PIC X(7) VALUE 'O0482  '.
               10  FILLER           PIC X(7) VALUE 'O0732  '.
               10  FILLER           PIC X(7) VALUE 'O084   '.
               10  FILLER           PIC X(7) VALUE 'O904   '.
               10  FILLER           PIC X(7) VALUE 'T795XXA'.
           05  FILLER     REDEFINES CAT4-TABLE-DATA.
               10  CAT4-DATA        OCCURS 10 TIMES
                                    ASCENDING KEY IS CAT4-CODE
                                    INDEXED BY IX-CAT4.
                   15  CAT4-CODE    PIC X(7).

      ****************************************************************
      * RENAL FAILURE, CHRONIC                                       *
      ****************************************************************
       01 CAT5-TABLE.
           05  CAT5-TABLE-DATA.
               10  FILLER           PIC X(7) VALUE 'I120   '.
               10  FILLER           PIC X(7) VALUE 'I1311  '.
               10  FILLER           PIC X(7) VALUE 'I132   '.
               10  FILLER           PIC X(7) VALUE 'N1830  '.
               10  FILLER           PIC X(7) VALUE 'N1831  '.
               10  FILLER           PIC X(7) VALUE 'N1832  '.
               10  FILLER           PIC X(7) VALUE 'N184   '.
               10  FILLER           PIC X(7) VALUE 'N185   '.
               10  FILLER           PIC X(7) VALUE 'N186   '.
               10  FILLER           PIC X(7) VALUE 'N189   '.
               10  FILLER           PIC X(7) VALUE 'N19    '.
               10  FILLER           PIC X(7) VALUE 'Z4901  '.
               10  FILLER           PIC X(7) VALUE 'Z4902  '.
               10  FILLER           PIC X(7) VALUE 'Z4931  '.
               10  FILLER           PIC X(7) VALUE 'Z9115  '.
               10  FILLER           PIC X(7) VALUE 'Z992   '.
           05  FILLER     REDEFINES CAT5-TABLE-DATA.
               10  CAT5-DATA        OCCURS 16 TIMES
                                    ASCENDING KEY IS CAT5-CODE
                                    INDEXED BY IX-CAT5.
                   15  CAT5-CODE    PIC X(7).

      ****************************************************************
      * ONCOLOGY TREATMENT - DIAGNOSIS CODES                         *
      ****************************************************************
       01 CAT6-TABLE.
           05  CAT6-TABLE-DATA.
               10  FILLER           PIC X(7) VALUE 'C000   '.
               10  FILLER           PIC X(7) VALUE 'C001   '.
               10  FILLER           PIC X(7) VALUE 'C003   '.
               10  FILLER           PIC X(7) VALUE 'C004   '.
               10  FILLER           PIC X(7) VALUE 'C006   '.
               10  FILLER           PIC X(7) VALUE 'C008   '.
               10  FILLER           PIC X(7) VALUE 'C01    '.
               10  FILLER           PIC X(7) VALUE 'C020   '.
               10  FILLER           PIC X(7) VALUE 'C021   '.
               10  FILLER           PIC X(7) VALUE 'C022   '.
               10  FILLER           PIC X(7) VALUE 'C023   '.
               10  FILLER           PIC X(7) VALUE 'C024   '.
               10  FILLER           PIC X(7) VALUE 'C028   '.
               10  FILLER           PIC X(7) VALUE 'C030   '.
               10  FILLER           PIC X(7) VALUE 'C031   '.
               10  FILLER           PIC X(7) VALUE 'C040   '.
               10  FILLER           PIC X(7) VALUE 'C041   '.
               10  FILLER           PIC X(7) VALUE 'C048   '.
               10  FILLER           PIC X(7) VALUE 'C050   '.
               10  FILLER           PIC X(7) VALUE 'C051   '.
               10  FILLER           PIC X(7) VALUE 'C052   '.
               10  FILLER           PIC X(7) VALUE 'C058   '.
               10  FILLER           PIC X(7) VALUE 'C060   '.
               10  FILLER           PIC X(7) VALUE 'C061   '.
               10  FILLER           PIC X(7) VALUE 'C062   '.
               10  FILLER           PIC X(7) VALUE 'C0680  '.
               10  FILLER           PIC X(7) VALUE 'C0689  '.
               10  FILLER           PIC X(7) VALUE 'C07    '.
               10  FILLER           PIC X(7) VALUE 'C080   '.
               10  FILLER           PIC X(7) VALUE 'C081   '.
               10  FILLER           PIC X(7) VALUE 'C089   '.
               10  FILLER           PIC X(7) VALUE 'C090   '.
               10  FILLER           PIC X(7) VALUE 'C091   '.
               10  FILLER           PIC X(7) VALUE 'C098   '.
               10  FILLER           PIC X(7) VALUE 'C099   '.
               10  FILLER           PIC X(7) VALUE 'C100   '.
               10  FILLER           PIC X(7) VALUE 'C101   '.
               10  FILLER           PIC X(7) VALUE 'C102   '.
               10  FILLER           PIC X(7) VALUE 'C103   '.
               10  FILLER           PIC X(7) VALUE 'C104   '.
               10  FILLER           PIC X(7) VALUE 'C108   '.
               10  FILLER           PIC X(7) VALUE 'C109   '.
               10  FILLER           PIC X(7) VALUE 'C110   '.
               10  FILLER           PIC X(7) VALUE 'C111   '.
               10  FILLER           PIC X(7) VALUE 'C112   '.
               10  FILLER           PIC X(7) VALUE 'C113   '.
               10  FILLER           PIC X(7) VALUE 'C118   '.
               10  FILLER           PIC X(7) VALUE 'C119   '.
               10  FILLER           PIC X(7) VALUE 'C12    '.
               10  FILLER           PIC X(7) VALUE 'C130   '.
               10  FILLER           PIC X(7) VALUE 'C131   '.
               10  FILLER           PIC X(7) VALUE 'C132   '.
               10  FILLER           PIC X(7) VALUE 'C138   '.
               10  FILLER           PIC X(7) VALUE 'C139   '.
               10  FILLER           PIC X(7) VALUE 'C140   '.
               10  FILLER           PIC X(7) VALUE 'C142   '.
               10  FILLER           PIC X(7) VALUE 'C148   '.
               10  FILLER           PIC X(7) VALUE 'C153   '.
               10  FILLER           PIC X(7) VALUE 'C154   '.
               10  FILLER           PIC X(7) VALUE 'C155   '.
               10  FILLER           PIC X(7) VALUE 'C158   '.
               10  FILLER           PIC X(7) VALUE 'C159   '.
               10  FILLER           PIC X(7) VALUE 'C160   '.
               10  FILLER           PIC X(7) VALUE 'C161   '.
               10  FILLER           PIC X(7) VALUE 'C162   '.
               10  FILLER           PIC X(7) VALUE 'C163   '.
               10  FILLER           PIC X(7) VALUE 'C164   '.
               10  FILLER           PIC X(7) VALUE 'C165   '.
               10  FILLER           PIC X(7) VALUE 'C166   '.
               10  FILLER           PIC X(7) VALUE 'C168   '.
               10  FILLER           PIC X(7) VALUE 'C169   '.
               10  FILLER           PIC X(7) VALUE 'C170   '.
               10  FILLER           PIC X(7) VALUE 'C171   '.
               10  FILLER           PIC X(7) VALUE 'C172   '.
               10  FILLER           PIC X(7) VALUE 'C173   '.
               10  FILLER           PIC X(7) VALUE 'C178   '.
               10  FILLER           PIC X(7) VALUE 'C179   '.
               10  FILLER           PIC X(7) VALUE 'C180   '.
               10  FILLER           PIC X(7) VALUE 'C181   '.
               10  FILLER           PIC X(7) VALUE 'C182   '.
               10  FILLER           PIC X(7) VALUE 'C183   '.
               10  FILLER           PIC X(7) VALUE 'C184   '.
               10  FILLER           PIC X(7) VALUE 'C185   '.
               10  FILLER           PIC X(7) VALUE 'C186   '.
               10  FILLER           PIC X(7) VALUE 'C187   '.
               10  FILLER           PIC X(7) VALUE 'C188   '.
               10  FILLER           PIC X(7) VALUE 'C189   '.
               10  FILLER           PIC X(7) VALUE 'C19    '.
               10  FILLER           PIC X(7) VALUE 'C20    '.
               10  FILLER           PIC X(7) VALUE 'C210   '.
               10  FILLER           PIC X(7) VALUE 'C211   '.
               10  FILLER           PIC X(7) VALUE 'C212   '.
               10  FILLER           PIC X(7) VALUE 'C218   '.
               10  FILLER           PIC X(7) VALUE 'C220   '.
               10  FILLER           PIC X(7) VALUE 'C221   '.
               10  FILLER           PIC X(7) VALUE 'C222   '.
               10  FILLER           PIC X(7) VALUE 'C223   '.
               10  FILLER           PIC X(7) VALUE 'C224   '.
               10  FILLER           PIC X(7) VALUE 'C227   '.
               10  FILLER           PIC X(7) VALUE 'C228   '.
               10  FILLER           PIC X(7) VALUE 'C229   '.
               10  FILLER           PIC X(7) VALUE 'C23    '.
               10  FILLER           PIC X(7) VALUE 'C240   '.
               10  FILLER           PIC X(7) VALUE 'C241   '.
               10  FILLER           PIC X(7) VALUE 'C248   '.
               10  FILLER           PIC X(7) VALUE 'C249   '.
               10  FILLER           PIC X(7) VALUE 'C250   '.
               10  FILLER           PIC X(7) VALUE 'C251   '.
               10  FILLER           PIC X(7) VALUE 'C252   '.
               10  FILLER           PIC X(7) VALUE 'C253   '.
               10  FILLER           PIC X(7) VALUE 'C254   '.
               10  FILLER           PIC X(7) VALUE 'C257   '.
               10  FILLER           PIC X(7) VALUE 'C258   '.
               10  FILLER           PIC X(7) VALUE 'C259   '.
               10  FILLER           PIC X(7) VALUE 'C260   '.
               10  FILLER           PIC X(7) VALUE 'C261   '.
               10  FILLER           PIC X(7) VALUE 'C269   '.
               10  FILLER           PIC X(7) VALUE 'C300   '.
               10  FILLER           PIC X(7) VALUE 'C301   '.
               10  FILLER           PIC X(7) VALUE 'C310   '.
               10  FILLER           PIC X(7) VALUE 'C311   '.
               10  FILLER           PIC X(7) VALUE 'C312   '.
               10  FILLER           PIC X(7) VALUE 'C313   '.
               10  FILLER           PIC X(7) VALUE 'C318   '.
               10  FILLER           PIC X(7) VALUE 'C319   '.
               10  FILLER           PIC X(7) VALUE 'C320   '.
               10  FILLER           PIC X(7) VALUE 'C321   '.
               10  FILLER           PIC X(7) VALUE 'C322   '.
               10  FILLER           PIC X(7) VALUE 'C323   '.
               10  FILLER           PIC X(7) VALUE 'C328   '.
               10  FILLER           PIC X(7) VALUE 'C329   '.
               10  FILLER           PIC X(7) VALUE 'C33    '.
               10  FILLER           PIC X(7) VALUE 'C3400  '.
               10  FILLER           PIC X(7) VALUE 'C3401  '.
               10  FILLER           PIC X(7) VALUE 'C3402  '.
               10  FILLER           PIC X(7) VALUE 'C3410  '.
               10  FILLER           PIC X(7) VALUE 'C3411  '.
               10  FILLER           PIC X(7) VALUE 'C3412  '.
               10  FILLER           PIC X(7) VALUE 'C342   '.
               10  FILLER           PIC X(7) VALUE 'C3430  '.
               10  FILLER           PIC X(7) VALUE 'C3431  '.
               10  FILLER           PIC X(7) VALUE 'C3432  '.
               10  FILLER           PIC X(7) VALUE 'C3480  '.
               10  FILLER           PIC X(7) VALUE 'C3481  '.
               10  FILLER           PIC X(7) VALUE 'C3482  '.
               10  FILLER           PIC X(7) VALUE 'C3491  '.
               10  FILLER           PIC X(7) VALUE 'C3492  '.
               10  FILLER           PIC X(7) VALUE 'C37    '.
               10  FILLER           PIC X(7) VALUE 'C380   '.
               10  FILLER           PIC X(7) VALUE 'C381   '.
               10  FILLER           PIC X(7) VALUE 'C382   '.
               10  FILLER           PIC X(7) VALUE 'C383   '.
               10  FILLER           PIC X(7) VALUE 'C384   '.
               10  FILLER           PIC X(7) VALUE 'C388   '.
               10  FILLER           PIC X(7) VALUE 'C390   '.
               10  FILLER           PIC X(7) VALUE 'C399   '.
               10  FILLER           PIC X(7) VALUE 'C4A0   '.
               10  FILLER           PIC X(7) VALUE 'C4A111 '.
               10  FILLER           PIC X(7) VALUE 'C4A112 '.
               10  FILLER           PIC X(7) VALUE 'C4A121 '.
               10  FILLER           PIC X(7) VALUE 'C4A122 '.
               10  FILLER           PIC X(7) VALUE 'C4A21  '.
               10  FILLER           PIC X(7) VALUE 'C4A22  '.
               10  FILLER           PIC X(7) VALUE 'C4A30  '.
               10  FILLER           PIC X(7) VALUE 'C4A31  '.
               10  FILLER           PIC X(7) VALUE 'C4A39  '.
               10  FILLER           PIC X(7) VALUE 'C4A4   '.
               10  FILLER           PIC X(7) VALUE 'C4A51  '.
               10  FILLER           PIC X(7) VALUE 'C4A52  '.
               10  FILLER           PIC X(7) VALUE 'C4A59  '.
               10  FILLER           PIC X(7) VALUE 'C4A61  '.
               10  FILLER           PIC X(7) VALUE 'C4A62  '.
               10  FILLER           PIC X(7) VALUE 'C4A71  '.
               10  FILLER           PIC X(7) VALUE 'C4A72  '.
               10  FILLER           PIC X(7) VALUE 'C4A8   '.
               10  FILLER           PIC X(7) VALUE 'C4001  '.
               10  FILLER           PIC X(7) VALUE 'C4002  '.
               10  FILLER           PIC X(7) VALUE 'C4011  '.
               10  FILLER           PIC X(7) VALUE 'C4012  '.
               10  FILLER           PIC X(7) VALUE 'C4021  '.
               10  FILLER           PIC X(7) VALUE 'C4022  '.
               10  FILLER           PIC X(7) VALUE 'C4031  '.
               10  FILLER           PIC X(7) VALUE 'C4032  '.
               10  FILLER           PIC X(7) VALUE 'C4081  '.
               10  FILLER           PIC X(7) VALUE 'C4082  '.
               10  FILLER           PIC X(7) VALUE 'C4091  '.
               10  FILLER           PIC X(7) VALUE 'C4092  '.
               10  FILLER           PIC X(7) VALUE 'C410   '.
               10  FILLER           PIC X(7) VALUE 'C411   '.
               10  FILLER           PIC X(7) VALUE 'C412   '.
               10  FILLER           PIC X(7) VALUE 'C413   '.
               10  FILLER           PIC X(7) VALUE 'C414   '.
               10  FILLER           PIC X(7) VALUE 'C419   '.
               10  FILLER           PIC X(7) VALUE 'C430   '.
               10  FILLER           PIC X(7) VALUE 'C43111 '.
               10  FILLER           PIC X(7) VALUE 'C43112 '.
               10  FILLER           PIC X(7) VALUE 'C43121 '.
               10  FILLER           PIC X(7) VALUE 'C43122 '.
               10  FILLER           PIC X(7) VALUE 'C4321  '.
               10  FILLER           PIC X(7) VALUE 'C4322  '.
               10  FILLER           PIC X(7) VALUE 'C4330  '.
               10  FILLER           PIC X(7) VALUE 'C4331  '.
               10  FILLER           PIC X(7) VALUE 'C4339  '.
               10  FILLER           PIC X(7) VALUE 'C434   '.
               10  FILLER           PIC X(7) VALUE 'C4351  '.
               10  FILLER           PIC X(7) VALUE 'C4352  '.
               10  FILLER           PIC X(7) VALUE 'C4359  '.
               10  FILLER           PIC X(7) VALUE 'C4361  '.
               10  FILLER           PIC X(7) VALUE 'C4362  '.
               10  FILLER           PIC X(7) VALUE 'C4371  '.
               10  FILLER           PIC X(7) VALUE 'C4372  '.
               10  FILLER           PIC X(7) VALUE 'C438   '.
               10  FILLER           PIC X(7) VALUE 'C4400  '.
               10  FILLER           PIC X(7) VALUE 'C4401  '.
               10  FILLER           PIC X(7) VALUE 'C4402  '.
               10  FILLER           PIC X(7) VALUE 'C4409  '.
               10  FILLER           PIC X(7) VALUE 'C441021'.
               10  FILLER           PIC X(7) VALUE 'C441022'.
               10  FILLER           PIC X(7) VALUE 'C441091'.
               10  FILLER           PIC X(7) VALUE 'C441092'.
               10  FILLER           PIC X(7) VALUE 'C441121'.
               10  FILLER           PIC X(7) VALUE 'C441122'.
               10  FILLER           PIC X(7) VALUE 'C441191'.
               10  FILLER           PIC X(7) VALUE 'C441192'.
               10  FILLER           PIC X(7) VALUE 'C441221'.
               10  FILLER           PIC X(7) VALUE 'C441222'.
               10  FILLER           PIC X(7) VALUE 'C441291'.
               10  FILLER           PIC X(7) VALUE 'C441292'.
               10  FILLER           PIC X(7) VALUE 'C441321'.
               10  FILLER           PIC X(7) VALUE 'C441322'.
               10  FILLER           PIC X(7) VALUE 'C441391'.
               10  FILLER           PIC X(7) VALUE 'C441392'.
               10  FILLER           PIC X(7) VALUE 'C441921'.
               10  FILLER           PIC X(7) VALUE 'C441922'.
               10  FILLER           PIC X(7) VALUE 'C441991'.
               10  FILLER           PIC X(7) VALUE 'C441992'.
               10  FILLER           PIC X(7) VALUE 'C44202 '.
               10  FILLER           PIC X(7) VALUE 'C44209 '.
               10  FILLER           PIC X(7) VALUE 'C44212 '.
               10  FILLER           PIC X(7) VALUE 'C44219 '.
               10  FILLER           PIC X(7) VALUE 'C44222 '.
               10  FILLER           PIC X(7) VALUE 'C44229 '.
               10  FILLER           PIC X(7) VALUE 'C44292 '.
               10  FILLER           PIC X(7) VALUE 'C44299 '.
               10  FILLER           PIC X(7) VALUE 'C44300 '.
               10  FILLER           PIC X(7) VALUE 'C44301 '.
               10  FILLER           PIC X(7) VALUE 'C44309 '.
               10  FILLER           PIC X(7) VALUE 'C44310 '.
               10  FILLER           PIC X(7) VALUE 'C44311 '.
               10  FILLER           PIC X(7) VALUE 'C44319 '.
               10  FILLER           PIC X(7) VALUE 'C44320 '.
               10  FILLER           PIC X(7) VALUE 'C44321 '.
               10  FILLER           PIC X(7) VALUE 'C44329 '.
               10  FILLER           PIC X(7) VALUE 'C44390 '.
               10  FILLER           PIC X(7) VALUE 'C44391 '.
               10  FILLER           PIC X(7) VALUE 'C44399 '.
               10  FILLER           PIC X(7) VALUE 'C4440  '.
               10  FILLER           PIC X(7) VALUE 'C4441  '.
               10  FILLER           PIC X(7) VALUE 'C4442  '.
               10  FILLER           PIC X(7) VALUE 'C4449  '.
               10  FILLER           PIC X(7) VALUE 'C44500 '.
               10  FILLER           PIC X(7) VALUE 'C44501 '.
               10  FILLER           PIC X(7) VALUE 'C44509 '.
               10  FILLER           PIC X(7) VALUE 'C44510 '.
               10  FILLER           PIC X(7) VALUE 'C44511 '.
               10  FILLER           PIC X(7) VALUE 'C44519 '.
               10  FILLER           PIC X(7) VALUE 'C44520 '.
               10  FILLER           PIC X(7) VALUE 'C44521 '.
               10  FILLER           PIC X(7) VALUE 'C44529 '.
               10  FILLER           PIC X(7) VALUE 'C44590 '.
               10  FILLER           PIC X(7) VALUE 'C44591 '.
               10  FILLER           PIC X(7) VALUE 'C44599 '.
               10  FILLER           PIC X(7) VALUE 'C44602 '.
               10  FILLER           PIC X(7) VALUE 'C44609 '.
               10  FILLER           PIC X(7) VALUE 'C44612 '.
               10  FILLER           PIC X(7) VALUE 'C44619 '.
               10  FILLER           PIC X(7) VALUE 'C44622 '.
               10  FILLER           PIC X(7) VALUE 'C44629 '.
               10  FILLER           PIC X(7) VALUE 'C44692 '.
               10  FILLER           PIC X(7) VALUE 'C44699 '.
               10  FILLER           PIC X(7) VALUE 'C44702 '.
               10  FILLER           PIC X(7) VALUE 'C44709 '.
               10  FILLER           PIC X(7) VALUE 'C44712 '.
               10  FILLER           PIC X(7) VALUE 'C44719 '.
               10  FILLER           PIC X(7) VALUE 'C44722 '.
               10  FILLER           PIC X(7) VALUE 'C44729 '.
               10  FILLER           PIC X(7) VALUE 'C44792 '.
               10  FILLER           PIC X(7) VALUE 'C44799 '.
               10  FILLER           PIC X(7) VALUE 'C4480  '.
               10  FILLER           PIC X(7) VALUE 'C4481  '.
               10  FILLER           PIC X(7) VALUE 'C4482  '.
               10  FILLER           PIC X(7) VALUE 'C4489  '.
               10  FILLER           PIC X(7) VALUE 'C450   '.
               10  FILLER           PIC X(7) VALUE 'C451   '.
               10  FILLER           PIC X(7) VALUE 'C452   '.
               10  FILLER           PIC X(7) VALUE 'C457   '.
               10  FILLER           PIC X(7) VALUE 'C459   '.
               10  FILLER           PIC X(7) VALUE 'C460   '.
               10  FILLER           PIC X(7) VALUE 'C461   '.
               10  FILLER           PIC X(7) VALUE 'C462   '.
               10  FILLER           PIC X(7) VALUE 'C463   '.
               10  FILLER           PIC X(7) VALUE 'C464   '.
               10  FILLER           PIC X(7) VALUE 'C4650  '.
               10  FILLER           PIC X(7) VALUE 'C4651  '.
               10  FILLER           PIC X(7) VALUE 'C4652  '.
               10  FILLER           PIC X(7) VALUE 'C467   '.
               10  FILLER           PIC X(7) VALUE 'C469   '.
               10  FILLER           PIC X(7) VALUE 'C470   '.
               10  FILLER           PIC X(7) VALUE 'C4711  '.
               10  FILLER           PIC X(7) VALUE 'C4712  '.
               10  FILLER           PIC X(7) VALUE 'C4721  '.
               10  FILLER           PIC X(7) VALUE 'C4722  '.
               10  FILLER           PIC X(7) VALUE 'C473   '.
               10  FILLER           PIC X(7) VALUE 'C474   '.
               10  FILLER           PIC X(7) VALUE 'C475   '.
               10  FILLER           PIC X(7) VALUE 'C476   '.
               10  FILLER           PIC X(7) VALUE 'C478   '.
               10  FILLER           PIC X(7) VALUE 'C480   '.
               10  FILLER           PIC X(7) VALUE 'C481   '.
               10  FILLER           PIC X(7) VALUE 'C482   '.
               10  FILLER           PIC X(7) VALUE 'C488   '.
               10  FILLER           PIC X(7) VALUE 'C49A0  '.
               10  FILLER           PIC X(7) VALUE 'C49A1  '.
               10  FILLER           PIC X(7) VALUE 'C49A2  '.
               10  FILLER           PIC X(7) VALUE 'C49A3  '.
               10  FILLER           PIC X(7) VALUE 'C49A4  '.
               10  FILLER           PIC X(7) VALUE 'C49A5  '.
               10  FILLER           PIC X(7) VALUE 'C49A9  '.
               10  FILLER           PIC X(7) VALUE 'C490   '.
               10  FILLER           PIC X(7) VALUE 'C4911  '.
               10  FILLER           PIC X(7) VALUE 'C4912  '.
               10  FILLER           PIC X(7) VALUE 'C4921  '.
               10  FILLER           PIC X(7) VALUE 'C4922  '.
               10  FILLER           PIC X(7) VALUE 'C493   '.
               10  FILLER           PIC X(7) VALUE 'C494   '.
               10  FILLER           PIC X(7) VALUE 'C495   '.
               10  FILLER           PIC X(7) VALUE 'C496   '.
               10  FILLER           PIC X(7) VALUE 'C498   '.
               10  FILLER           PIC X(7) VALUE 'C50011 '.
               10  FILLER           PIC X(7) VALUE 'C50012 '.
               10  FILLER           PIC X(7) VALUE 'C50021 '.
               10  FILLER           PIC X(7) VALUE 'C50022 '.
               10  FILLER           PIC X(7) VALUE 'C50111 '.
               10  FILLER           PIC X(7) VALUE 'C50112 '.
               10  FILLER           PIC X(7) VALUE 'C50121 '.
               10  FILLER           PIC X(7) VALUE 'C50122 '.
               10  FILLER           PIC X(7) VALUE 'C50211 '.
               10  FILLER           PIC X(7) VALUE 'C50212 '.
               10  FILLER           PIC X(7) VALUE 'C50221 '.
               10  FILLER           PIC X(7) VALUE 'C50222 '.
               10  FILLER           PIC X(7) VALUE 'C50311 '.
               10  FILLER           PIC X(7) VALUE 'C50312 '.
               10  FILLER           PIC X(7) VALUE 'C50321 '.
               10  FILLER           PIC X(7) VALUE 'C50322 '.
               10  FILLER           PIC X(7) VALUE 'C50411 '.
               10  FILLER           PIC X(7) VALUE 'C50412 '.
               10  FILLER           PIC X(7) VALUE 'C50421 '.
               10  FILLER           PIC X(7) VALUE 'C50422 '.
               10  FILLER           PIC X(7) VALUE 'C50511 '.
               10  FILLER           PIC X(7) VALUE 'C50512 '.
               10  FILLER           PIC X(7) VALUE 'C50521 '.
               10  FILLER           PIC X(7) VALUE 'C50522 '.
               10  FILLER           PIC X(7) VALUE 'C50611 '.
               10  FILLER           PIC X(7) VALUE 'C50612 '.
               10  FILLER           PIC X(7) VALUE 'C50621 '.
               10  FILLER           PIC X(7) VALUE 'C50622 '.
               10  FILLER           PIC X(7) VALUE 'C50811 '.
               10  FILLER           PIC X(7) VALUE 'C50812 '.
               10  FILLER           PIC X(7) VALUE 'C50821 '.
               10  FILLER           PIC X(7) VALUE 'C50822 '.
               10  FILLER           PIC X(7) VALUE 'C50911 '.
               10  FILLER           PIC X(7) VALUE 'C50912 '.
               10  FILLER           PIC X(7) VALUE 'C50921 '.
               10  FILLER           PIC X(7) VALUE 'C50922 '.
               10  FILLER           PIC X(7) VALUE 'C510   '.
               10  FILLER           PIC X(7) VALUE 'C511   '.
               10  FILLER           PIC X(7) VALUE 'C512   '.
               10  FILLER           PIC X(7) VALUE 'C518   '.
               10  FILLER           PIC X(7) VALUE 'C519   '.
               10  FILLER           PIC X(7) VALUE 'C52    '.
               10  FILLER           PIC X(7) VALUE 'C530   '.
               10  FILLER           PIC X(7) VALUE 'C531   '.
               10  FILLER           PIC X(7) VALUE 'C538   '.
               10  FILLER           PIC X(7) VALUE 'C539   '.
               10  FILLER           PIC X(7) VALUE 'C540   '.
               10  FILLER           PIC X(7) VALUE 'C541   '.
               10  FILLER           PIC X(7) VALUE 'C542   '.
               10  FILLER           PIC X(7) VALUE 'C543   '.
               10  FILLER           PIC X(7) VALUE 'C548   '.
               10  FILLER           PIC X(7) VALUE 'C549   '.
               10  FILLER           PIC X(7) VALUE 'C55    '.
               10  FILLER           PIC X(7) VALUE 'C561   '.
               10  FILLER           PIC X(7) VALUE 'C562   '.
               10  FILLER           PIC X(7) VALUE 'C563   '.
               10  FILLER           PIC X(7) VALUE 'C569   '.
               10  FILLER           PIC X(7) VALUE 'C5700  '.
               10  FILLER           PIC X(7) VALUE 'C5701  '.
               10  FILLER           PIC X(7) VALUE 'C5702  '.
               10  FILLER           PIC X(7) VALUE 'C5710  '.
               10  FILLER           PIC X(7) VALUE 'C5711  '.
               10  FILLER           PIC X(7) VALUE 'C5712  '.
               10  FILLER           PIC X(7) VALUE 'C5720  '.
               10  FILLER           PIC X(7) VALUE 'C5721  '.
               10  FILLER           PIC X(7) VALUE 'C5722  '.
               10  FILLER           PIC X(7) VALUE 'C573   '.
               10  FILLER           PIC X(7) VALUE 'C574   '.
               10  FILLER           PIC X(7) VALUE 'C577   '.
               10  FILLER           PIC X(7) VALUE 'C578   '.
               10  FILLER           PIC X(7) VALUE 'C58    '.
               10  FILLER           PIC X(7) VALUE 'C600   '.
               10  FILLER           PIC X(7) VALUE 'C601   '.
               10  FILLER           PIC X(7) VALUE 'C602   '.
               10  FILLER           PIC X(7) VALUE 'C608   '.
               10  FILLER           PIC X(7) VALUE 'C609   '.
               10  FILLER           PIC X(7) VALUE 'C61    '.
               10  FILLER           PIC X(7) VALUE 'C6200  '.
               10  FILLER           PIC X(7) VALUE 'C6201  '.
               10  FILLER           PIC X(7) VALUE 'C6202  '.
               10  FILLER           PIC X(7) VALUE 'C6210  '.
               10  FILLER           PIC X(7) VALUE 'C6211  '.
               10  FILLER           PIC X(7) VALUE 'C6212  '.
               10  FILLER           PIC X(7) VALUE 'C6291  '.
               10  FILLER           PIC X(7) VALUE 'C6292  '.
               10  FILLER           PIC X(7) VALUE 'C6300  '.
               10  FILLER           PIC X(7) VALUE 'C6301  '.
               10  FILLER           PIC X(7) VALUE 'C6302  '.
               10  FILLER           PIC X(7) VALUE 'C6310  '.
               10  FILLER           PIC X(7) VALUE 'C6311  '.
               10  FILLER           PIC X(7) VALUE 'C6312  '.
               10  FILLER           PIC X(7) VALUE 'C632   '.
               10  FILLER           PIC X(7) VALUE 'C637   '.
               10  FILLER           PIC X(7) VALUE 'C638   '.
               10  FILLER           PIC X(7) VALUE 'C641   '.
               10  FILLER           PIC X(7) VALUE 'C642   '.
               10  FILLER           PIC X(7) VALUE 'C651   '.
               10  FILLER           PIC X(7) VALUE 'C652   '.
               10  FILLER           PIC X(7) VALUE 'C659   '.
               10  FILLER           PIC X(7) VALUE 'C661   '.
               10  FILLER           PIC X(7) VALUE 'C662   '.
               10  FILLER           PIC X(7) VALUE 'C669   '.
               10  FILLER           PIC X(7) VALUE 'C670   '.
               10  FILLER           PIC X(7) VALUE 'C671   '.
               10  FILLER           PIC X(7) VALUE 'C672   '.
               10  FILLER           PIC X(7) VALUE 'C673   '.
               10  FILLER           PIC X(7) VALUE 'C674   '.
               10  FILLER           PIC X(7) VALUE 'C675   '.
               10  FILLER           PIC X(7) VALUE 'C676   '.
               10  FILLER           PIC X(7) VALUE 'C677   '.
               10  FILLER           PIC X(7) VALUE 'C678   '.
               10  FILLER           PIC X(7) VALUE 'C679   '.
               10  FILLER           PIC X(7) VALUE 'C680   '.
               10  FILLER           PIC X(7) VALUE 'C681   '.
               10  FILLER           PIC X(7) VALUE 'C688   '.
               10  FILLER           PIC X(7) VALUE 'C689   '.
               10  FILLER           PIC X(7) VALUE 'C6901  '.
               10  FILLER           PIC X(7) VALUE 'C6902  '.
               10  FILLER           PIC X(7) VALUE 'C6911  '.
               10  FILLER           PIC X(7) VALUE 'C6912  '.
               10  FILLER           PIC X(7) VALUE 'C6920  '.
               10  FILLER           PIC X(7) VALUE 'C6921  '.
               10  FILLER           PIC X(7) VALUE 'C6922  '.
               10  FILLER           PIC X(7) VALUE 'C6930  '.
               10  FILLER           PIC X(7) VALUE 'C6931  '.
               10  FILLER           PIC X(7) VALUE 'C6932  '.
               10  FILLER           PIC X(7) VALUE 'C6940  '.
               10  FILLER           PIC X(7) VALUE 'C6941  '.
               10  FILLER           PIC X(7) VALUE 'C6942  '.
               10  FILLER           PIC X(7) VALUE 'C6951  '.
               10  FILLER           PIC X(7) VALUE 'C6952  '.
               10  FILLER           PIC X(7) VALUE 'C6961  '.
               10  FILLER           PIC X(7) VALUE 'C6962  '.
               10  FILLER           PIC X(7) VALUE 'C6981  '.
               10  FILLER           PIC X(7) VALUE 'C6982  '.
               10  FILLER           PIC X(7) VALUE 'C6991  '.
               10  FILLER           PIC X(7) VALUE 'C6992  '.
               10  FILLER           PIC X(7) VALUE 'C7A00  '.
               10  FILLER           PIC X(7) VALUE 'C7A010 '.
               10  FILLER           PIC X(7) VALUE 'C7A011 '.
               10  FILLER           PIC X(7) VALUE 'C7A012 '.
               10  FILLER           PIC X(7) VALUE 'C7A019 '.
               10  FILLER           PIC X(7) VALUE 'C7A020 '.
               10  FILLER           PIC X(7) VALUE 'C7A021 '.
               10  FILLER           PIC X(7) VALUE 'C7A022 '.
               10  FILLER           PIC X(7) VALUE 'C7A023 '.
               10  FILLER           PIC X(7) VALUE 'C7A024 '.
               10  FILLER           PIC X(7) VALUE 'C7A025 '.
               10  FILLER           PIC X(7) VALUE 'C7A026 '.
               10  FILLER           PIC X(7) VALUE 'C7A029 '.
               10  FILLER           PIC X(7) VALUE 'C7A090 '.
               10  FILLER           PIC X(7) VALUE 'C7A091 '.
               10  FILLER           PIC X(7) VALUE 'C7A092 '.
               10  FILLER           PIC X(7) VALUE 'C7A093 '.
               10  FILLER           PIC X(7) VALUE 'C7A094 '.
               10  FILLER           PIC X(7) VALUE 'C7A095 '.
               10  FILLER           PIC X(7) VALUE 'C7A096 '.
               10  FILLER           PIC X(7) VALUE 'C7A098 '.
               10  FILLER           PIC X(7) VALUE 'C7A1   '.
               10  FILLER           PIC X(7) VALUE 'C7A8   '.
               10  FILLER           PIC X(7) VALUE 'C7B00  '.
               10  FILLER           PIC X(7) VALUE 'C7B01  '.
               10  FILLER           PIC X(7) VALUE 'C7B02  '.
               10  FILLER           PIC X(7) VALUE 'C7B03  '.
               10  FILLER           PIC X(7) VALUE 'C7B04  '.
               10  FILLER           PIC X(7) VALUE 'C7B09  '.
               10  FILLER           PIC X(7) VALUE 'C7B1   '.
               10  FILLER           PIC X(7) VALUE 'C7B8   '.
               10  FILLER           PIC X(7) VALUE 'C700   '.
               10  FILLER           PIC X(7) VALUE 'C701   '.
               10  FILLER           PIC X(7) VALUE 'C709   '.
               10  FILLER           PIC X(7) VALUE 'C710   '.
               10  FILLER           PIC X(7) VALUE 'C711   '.
               10  FILLER           PIC X(7) VALUE 'C712   '.
               10  FILLER           PIC X(7) VALUE 'C713   '.
               10  FILLER           PIC X(7) VALUE 'C714   '.
               10  FILLER           PIC X(7) VALUE 'C715   '.
               10  FILLER           PIC X(7) VALUE 'C716   '.
               10  FILLER           PIC X(7) VALUE 'C717   '.
               10  FILLER           PIC X(7) VALUE 'C718   '.
               10  FILLER           PIC X(7) VALUE 'C719   '.
               10  FILLER           PIC X(7) VALUE 'C720   '.
               10  FILLER           PIC X(7) VALUE 'C721   '.
               10  FILLER           PIC X(7) VALUE 'C7220  '.
               10  FILLER           PIC X(7) VALUE 'C7221  '.
               10  FILLER           PIC X(7) VALUE 'C7222  '.
               10  FILLER           PIC X(7) VALUE 'C7230  '.
               10  FILLER           PIC X(7) VALUE 'C7231  '.
               10  FILLER           PIC X(7) VALUE 'C7232  '.
               10  FILLER           PIC X(7) VALUE 'C7240  '.
               10  FILLER           PIC X(7) VALUE 'C7241  '.
               10  FILLER           PIC X(7) VALUE 'C7242  '.
               10  FILLER           PIC X(7) VALUE 'C7250  '.
               10  FILLER           PIC X(7) VALUE 'C7259  '.
               10  FILLER           PIC X(7) VALUE 'C73    '.
               10  FILLER           PIC X(7) VALUE 'C7400  '.
               10  FILLER           PIC X(7) VALUE 'C7401  '.
               10  FILLER           PIC X(7) VALUE 'C7402  '.
               10  FILLER           PIC X(7) VALUE 'C7410  '.
               10  FILLER           PIC X(7) VALUE 'C7411  '.
               10  FILLER           PIC X(7) VALUE 'C7412  '.
               10  FILLER           PIC X(7) VALUE 'C7491  '.
               10  FILLER           PIC X(7) VALUE 'C7492  '.
               10  FILLER           PIC X(7) VALUE 'C750   '.
               10  FILLER           PIC X(7) VALUE 'C751   '.
               10  FILLER           PIC X(7) VALUE 'C752   '.
               10  FILLER           PIC X(7) VALUE 'C753   '.
               10  FILLER           PIC X(7) VALUE 'C754   '.
               10  FILLER           PIC X(7) VALUE 'C755   '.
               10  FILLER           PIC X(7) VALUE 'C758   '.
               10  FILLER           PIC X(7) VALUE 'C759   '.
               10  FILLER           PIC X(7) VALUE 'C760   '.
               10  FILLER           PIC X(7) VALUE 'C761   '.
               10  FILLER           PIC X(7) VALUE 'C762   '.
               10  FILLER           PIC X(7) VALUE 'C763   '.
               10  FILLER           PIC X(7) VALUE 'C7641  '.
               10  FILLER           PIC X(7) VALUE 'C7642  '.
               10  FILLER           PIC X(7) VALUE 'C7651  '.
               10  FILLER           PIC X(7) VALUE 'C7652  '.
               10  FILLER           PIC X(7) VALUE 'C768   '.
               10  FILLER           PIC X(7) VALUE 'C770   '.
               10  FILLER           PIC X(7) VALUE 'C771   '.
               10  FILLER           PIC X(7) VALUE 'C772   '.
               10  FILLER           PIC X(7) VALUE 'C773   '.
               10  FILLER           PIC X(7) VALUE 'C774   '.
               10  FILLER           PIC X(7) VALUE 'C775   '.
               10  FILLER           PIC X(7) VALUE 'C778   '.
               10  FILLER           PIC X(7) VALUE 'C7800  '.
               10  FILLER           PIC X(7) VALUE 'C7801  '.
               10  FILLER           PIC X(7) VALUE 'C7802  '.
               10  FILLER           PIC X(7) VALUE 'C781   '.
               10  FILLER           PIC X(7) VALUE 'C782   '.
               10  FILLER           PIC X(7) VALUE 'C7830  '.
               10  FILLER           PIC X(7) VALUE 'C7839  '.
               10  FILLER           PIC X(7) VALUE 'C784   '.
               10  FILLER           PIC X(7) VALUE 'C785   '.
               10  FILLER           PIC X(7) VALUE 'C786   '.
               10  FILLER           PIC X(7) VALUE 'C787   '.
               10  FILLER           PIC X(7) VALUE 'C7880  '.
               10  FILLER           PIC X(7) VALUE 'C7889  '.
               10  FILLER           PIC X(7) VALUE 'C7900  '.
               10  FILLER           PIC X(7) VALUE 'C7901  '.
               10  FILLER           PIC X(7) VALUE 'C7902  '.
               10  FILLER           PIC X(7) VALUE 'C7910  '.
               10  FILLER           PIC X(7) VALUE 'C7911  '.
               10  FILLER           PIC X(7) VALUE 'C7919  '.
               10  FILLER           PIC X(7) VALUE 'C792   '.
               10  FILLER           PIC X(7) VALUE 'C7931  '.
               10  FILLER           PIC X(7) VALUE 'C7932  '.
               10  FILLER           PIC X(7) VALUE 'C7940  '.
               10  FILLER           PIC X(7) VALUE 'C7949  '.
               10  FILLER           PIC X(7) VALUE 'C7951  '.
               10  FILLER           PIC X(7) VALUE 'C7952  '.
               10  FILLER           PIC X(7) VALUE 'C7960  '.
               10  FILLER           PIC X(7) VALUE 'C7961  '.
               10  FILLER           PIC X(7) VALUE 'C7962  '.
               10  FILLER           PIC X(7) VALUE 'C7963  '.
               10  FILLER           PIC X(7) VALUE 'C7970  '.
               10  FILLER           PIC X(7) VALUE 'C7971  '.
               10  FILLER           PIC X(7) VALUE 'C7972  '.
               10  FILLER           PIC X(7) VALUE 'C7981  '.
               10  FILLER           PIC X(7) VALUE 'C7982  '.
               10  FILLER           PIC X(7) VALUE 'C7989  '.
               10  FILLER           PIC X(7) VALUE 'C800   '.
               10  FILLER           PIC X(7) VALUE 'C801   '.
               10  FILLER           PIC X(7) VALUE 'C802   '.
               10  FILLER           PIC X(7) VALUE 'C8100  '.
               10  FILLER           PIC X(7) VALUE 'C8101  '.
               10  FILLER           PIC X(7) VALUE 'C8102  '.
               10  FILLER           PIC X(7) VALUE 'C8103  '.
               10  FILLER           PIC X(7) VALUE 'C8104  '.
               10  FILLER           PIC X(7) VALUE 'C8105  '.
               10  FILLER           PIC X(7) VALUE 'C8106  '.
               10  FILLER           PIC X(7) VALUE 'C8107  '.
               10  FILLER           PIC X(7) VALUE 'C8108  '.
               10  FILLER           PIC X(7) VALUE 'C8109  '.
               10  FILLER           PIC X(7) VALUE 'C8110  '.
               10  FILLER           PIC X(7) VALUE 'C8111  '.
               10  FILLER           PIC X(7) VALUE 'C8112  '.
               10  FILLER           PIC X(7) VALUE 'C8113  '.
               10  FILLER           PIC X(7) VALUE 'C8114  '.
               10  FILLER           PIC X(7) VALUE 'C8115  '.
               10  FILLER           PIC X(7) VALUE 'C8116  '.
               10  FILLER           PIC X(7) VALUE 'C8117  '.
               10  FILLER           PIC X(7) VALUE 'C8118  '.
               10  FILLER           PIC X(7) VALUE 'C8119  '.
               10  FILLER           PIC X(7) VALUE 'C8120  '.
               10  FILLER           PIC X(7) VALUE 'C8121  '.
               10  FILLER           PIC X(7) VALUE 'C8122  '.
               10  FILLER           PIC X(7) VALUE 'C8123  '.
               10  FILLER           PIC X(7) VALUE 'C8124  '.
               10  FILLER           PIC X(7) VALUE 'C8125  '.
               10  FILLER           PIC X(7) VALUE 'C8126  '.
               10  FILLER           PIC X(7) VALUE 'C8127  '.
               10  FILLER           PIC X(7) VALUE 'C8128  '.
               10  FILLER           PIC X(7) VALUE 'C8129  '.
               10  FILLER           PIC X(7) VALUE 'C8130  '.
               10  FILLER           PIC X(7) VALUE 'C8131  '.
               10  FILLER           PIC X(7) VALUE 'C8132  '.
               10  FILLER           PIC X(7) VALUE 'C8133  '.
               10  FILLER           PIC X(7) VALUE 'C8134  '.
               10  FILLER           PIC X(7) VALUE 'C8135  '.
               10  FILLER           PIC X(7) VALUE 'C8136  '.
               10  FILLER           PIC X(7) VALUE 'C8137  '.
               10  FILLER           PIC X(7) VALUE 'C8138  '.
               10  FILLER           PIC X(7) VALUE 'C8139  '.
               10  FILLER           PIC X(7) VALUE 'C8140  '.
               10  FILLER           PIC X(7) VALUE 'C8141  '.
               10  FILLER           PIC X(7) VALUE 'C8142  '.
               10  FILLER           PIC X(7) VALUE 'C8143  '.
               10  FILLER           PIC X(7) VALUE 'C8144  '.
               10  FILLER           PIC X(7) VALUE 'C8145  '.
               10  FILLER           PIC X(7) VALUE 'C8146  '.
               10  FILLER           PIC X(7) VALUE 'C8147  '.
               10  FILLER           PIC X(7) VALUE 'C8148  '.
               10  FILLER           PIC X(7) VALUE 'C8149  '.
               10  FILLER           PIC X(7) VALUE 'C8170  '.
               10  FILLER           PIC X(7) VALUE 'C8171  '.
               10  FILLER           PIC X(7) VALUE 'C8172  '.
               10  FILLER           PIC X(7) VALUE 'C8173  '.
               10  FILLER           PIC X(7) VALUE 'C8174  '.
               10  FILLER           PIC X(7) VALUE 'C8175  '.
               10  FILLER           PIC X(7) VALUE 'C8176  '.
               10  FILLER           PIC X(7) VALUE 'C8177  '.
               10  FILLER           PIC X(7) VALUE 'C8178  '.
               10  FILLER           PIC X(7) VALUE 'C8179  '.
               10  FILLER           PIC X(7) VALUE 'C8190  '.
               10  FILLER           PIC X(7) VALUE 'C8191  '.
               10  FILLER           PIC X(7) VALUE 'C8192  '.
               10  FILLER           PIC X(7) VALUE 'C8193  '.
               10  FILLER           PIC X(7) VALUE 'C8194  '.
               10  FILLER           PIC X(7) VALUE 'C8195  '.
               10  FILLER           PIC X(7) VALUE 'C8196  '.
               10  FILLER           PIC X(7) VALUE 'C8197  '.
               10  FILLER           PIC X(7) VALUE 'C8198  '.
               10  FILLER           PIC X(7) VALUE 'C8199  '.
               10  FILLER           PIC X(7) VALUE 'C8200  '.
               10  FILLER           PIC X(7) VALUE 'C8201  '.
               10  FILLER           PIC X(7) VALUE 'C8202  '.
               10  FILLER           PIC X(7) VALUE 'C8203  '.
               10  FILLER           PIC X(7) VALUE 'C8204  '.
               10  FILLER           PIC X(7) VALUE 'C8205  '.
               10  FILLER           PIC X(7) VALUE 'C8206  '.
               10  FILLER           PIC X(7) VALUE 'C8207  '.
               10  FILLER           PIC X(7) VALUE 'C8208  '.
               10  FILLER           PIC X(7) VALUE 'C8209  '.
               10  FILLER           PIC X(7) VALUE 'C8210  '.
               10  FILLER           PIC X(7) VALUE 'C8211  '.
               10  FILLER           PIC X(7) VALUE 'C8212  '.
               10  FILLER           PIC X(7) VALUE 'C8213  '.
               10  FILLER           PIC X(7) VALUE 'C8214  '.
               10  FILLER           PIC X(7) VALUE 'C8215  '.
               10  FILLER           PIC X(7) VALUE 'C8216  '.
               10  FILLER           PIC X(7) VALUE 'C8217  '.
               10  FILLER           PIC X(7) VALUE 'C8218  '.
               10  FILLER           PIC X(7) VALUE 'C8219  '.
               10  FILLER           PIC X(7) VALUE 'C8220  '.
               10  FILLER           PIC X(7) VALUE 'C8221  '.
               10  FILLER           PIC X(7) VALUE 'C8222  '.
               10  FILLER           PIC X(7) VALUE 'C8223  '.
               10  FILLER           PIC X(7) VALUE 'C8224  '.
               10  FILLER           PIC X(7) VALUE 'C8225  '.
               10  FILLER           PIC X(7) VALUE 'C8226  '.
               10  FILLER           PIC X(7) VALUE 'C8227  '.
               10  FILLER           PIC X(7) VALUE 'C8228  '.
               10  FILLER           PIC X(7) VALUE 'C8229  '.
               10  FILLER           PIC X(7) VALUE 'C8230  '.
               10  FILLER           PIC X(7) VALUE 'C8231  '.
               10  FILLER           PIC X(7) VALUE 'C8232  '.
               10  FILLER           PIC X(7) VALUE 'C8233  '.
               10  FILLER           PIC X(7) VALUE 'C8234  '.
               10  FILLER           PIC X(7) VALUE 'C8235  '.
               10  FILLER           PIC X(7) VALUE 'C8236  '.
               10  FILLER           PIC X(7) VALUE 'C8237  '.
               10  FILLER           PIC X(7) VALUE 'C8238  '.
               10  FILLER           PIC X(7) VALUE 'C8239  '.
               10  FILLER           PIC X(7) VALUE 'C8240  '.
               10  FILLER           PIC X(7) VALUE 'C8241  '.
               10  FILLER           PIC X(7) VALUE 'C8242  '.
               10  FILLER           PIC X(7) VALUE 'C8243  '.
               10  FILLER           PIC X(7) VALUE 'C8244  '.
               10  FILLER           PIC X(7) VALUE 'C8245  '.
               10  FILLER           PIC X(7) VALUE 'C8246  '.
               10  FILLER           PIC X(7) VALUE 'C8247  '.
               10  FILLER           PIC X(7) VALUE 'C8248  '.
               10  FILLER           PIC X(7) VALUE 'C8249  '.
               10  FILLER           PIC X(7) VALUE 'C8250  '.
               10  FILLER           PIC X(7) VALUE 'C8251  '.
               10  FILLER           PIC X(7) VALUE 'C8252  '.
               10  FILLER           PIC X(7) VALUE 'C8253  '.
               10  FILLER           PIC X(7) VALUE 'C8254  '.
               10  FILLER           PIC X(7) VALUE 'C8255  '.
               10  FILLER           PIC X(7) VALUE 'C8256  '.
               10  FILLER           PIC X(7) VALUE 'C8257  '.
               10  FILLER           PIC X(7) VALUE 'C8258  '.
               10  FILLER           PIC X(7) VALUE 'C8259  '.
               10  FILLER           PIC X(7) VALUE 'C8260  '.
               10  FILLER           PIC X(7) VALUE 'C8261  '.
               10  FILLER           PIC X(7) VALUE 'C8262  '.
               10  FILLER           PIC X(7) VALUE 'C8263  '.
               10  FILLER           PIC X(7) VALUE 'C8264  '.
               10  FILLER           PIC X(7) VALUE 'C8265  '.
               10  FILLER           PIC X(7) VALUE 'C8266  '.
               10  FILLER           PIC X(7) VALUE 'C8267  '.
               10  FILLER           PIC X(7) VALUE 'C8268  '.
               10  FILLER           PIC X(7) VALUE 'C8269  '.
               10  FILLER           PIC X(7) VALUE 'C8280  '.
               10  FILLER           PIC X(7) VALUE 'C8281  '.
               10  FILLER           PIC X(7) VALUE 'C8282  '.
               10  FILLER           PIC X(7) VALUE 'C8283  '.
               10  FILLER           PIC X(7) VALUE 'C8284  '.
               10  FILLER           PIC X(7) VALUE 'C8285  '.
               10  FILLER           PIC X(7) VALUE 'C8286  '.
               10  FILLER           PIC X(7) VALUE 'C8287  '.
               10  FILLER           PIC X(7) VALUE 'C8288  '.
               10  FILLER           PIC X(7) VALUE 'C8289  '.
               10  FILLER           PIC X(7) VALUE 'C8290  '.
               10  FILLER           PIC X(7) VALUE 'C8291  '.
               10  FILLER           PIC X(7) VALUE 'C8292  '.
               10  FILLER           PIC X(7) VALUE 'C8293  '.
               10  FILLER           PIC X(7) VALUE 'C8294  '.
               10  FILLER           PIC X(7) VALUE 'C8295  '.
               10  FILLER           PIC X(7) VALUE 'C8296  '.
               10  FILLER           PIC X(7) VALUE 'C8297  '.
               10  FILLER           PIC X(7) VALUE 'C8298  '.
               10  FILLER           PIC X(7) VALUE 'C8299  '.
               10  FILLER           PIC X(7) VALUE 'C8300  '.
               10  FILLER           PIC X(7) VALUE 'C8301  '.
               10  FILLER           PIC X(7) VALUE 'C8302  '.
               10  FILLER           PIC X(7) VALUE 'C8303  '.
               10  FILLER           PIC X(7) VALUE 'C8304  '.
               10  FILLER           PIC X(7) VALUE 'C8305  '.
               10  FILLER           PIC X(7) VALUE 'C8306  '.
               10  FILLER           PIC X(7) VALUE 'C8307  '.
               10  FILLER           PIC X(7) VALUE 'C8308  '.
               10  FILLER           PIC X(7) VALUE 'C8309  '.
               10  FILLER           PIC X(7) VALUE 'C8310  '.
               10  FILLER           PIC X(7) VALUE 'C8311  '.
               10  FILLER           PIC X(7) VALUE 'C8312  '.
               10  FILLER           PIC X(7) VALUE 'C8313  '.
               10  FILLER           PIC X(7) VALUE 'C8314  '.
               10  FILLER           PIC X(7) VALUE 'C8315  '.
               10  FILLER           PIC X(7) VALUE 'C8316  '.
               10  FILLER           PIC X(7) VALUE 'C8317  '.
               10  FILLER           PIC X(7) VALUE 'C8318  '.
               10  FILLER           PIC X(7) VALUE 'C8319  '.
               10  FILLER           PIC X(7) VALUE 'C8330  '.
               10  FILLER           PIC X(7) VALUE 'C8331  '.
               10  FILLER           PIC X(7) VALUE 'C8332  '.
               10  FILLER           PIC X(7) VALUE 'C8333  '.
               10  FILLER           PIC X(7) VALUE 'C8334  '.
               10  FILLER           PIC X(7) VALUE 'C8335  '.
               10  FILLER           PIC X(7) VALUE 'C8336  '.
               10  FILLER           PIC X(7) VALUE 'C8337  '.
               10  FILLER           PIC X(7) VALUE 'C8338  '.
               10  FILLER           PIC X(7) VALUE 'C8339  '.
               10  FILLER           PIC X(7) VALUE 'C8350  '.
               10  FILLER           PIC X(7) VALUE 'C8351  '.
               10  FILLER           PIC X(7) VALUE 'C8352  '.
               10  FILLER           PIC X(7) VALUE 'C8353  '.
               10  FILLER           PIC X(7) VALUE 'C8354  '.
               10  FILLER           PIC X(7) VALUE 'C8355  '.
               10  FILLER           PIC X(7) VALUE 'C8356  '.
               10  FILLER           PIC X(7) VALUE 'C8357  '.
               10  FILLER           PIC X(7) VALUE 'C8358  '.
               10  FILLER           PIC X(7) VALUE 'C8359  '.
               10  FILLER           PIC X(7) VALUE 'C8370  '.
               10  FILLER           PIC X(7) VALUE 'C8371  '.
               10  FILLER           PIC X(7) VALUE 'C8372  '.
               10  FILLER           PIC X(7) VALUE 'C8373  '.
               10  FILLER           PIC X(7) VALUE 'C8374  '.
               10  FILLER           PIC X(7) VALUE 'C8375  '.
               10  FILLER           PIC X(7) VALUE 'C8376  '.
               10  FILLER           PIC X(7) VALUE 'C8377  '.
               10  FILLER           PIC X(7) VALUE 'C8378  '.
               10  FILLER           PIC X(7) VALUE 'C8379  '.
               10  FILLER           PIC X(7) VALUE 'C8380  '.
               10  FILLER           PIC X(7) VALUE 'C8381  '.
               10  FILLER           PIC X(7) VALUE 'C8382  '.
               10  FILLER           PIC X(7) VALUE 'C8383  '.
               10  FILLER           PIC X(7) VALUE 'C8384  '.
               10  FILLER           PIC X(7) VALUE 'C8385  '.
               10  FILLER           PIC X(7) VALUE 'C8386  '.
               10  FILLER           PIC X(7) VALUE 'C8387  '.
               10  FILLER           PIC X(7) VALUE 'C8388  '.
               10  FILLER           PIC X(7) VALUE 'C8389  '.
               10  FILLER           PIC X(7) VALUE 'C8390  '.
               10  FILLER           PIC X(7) VALUE 'C8391  '.
               10  FILLER           PIC X(7) VALUE 'C8392  '.
               10  FILLER           PIC X(7) VALUE 'C8393  '.
               10  FILLER           PIC X(7) VALUE 'C8394  '.
               10  FILLER           PIC X(7) VALUE 'C8395  '.
               10  FILLER           PIC X(7) VALUE 'C8396  '.
               10  FILLER           PIC X(7) VALUE 'C8397  '.
               10  FILLER           PIC X(7) VALUE 'C8398  '.
               10  FILLER           PIC X(7) VALUE 'C8399  '.
               10  FILLER           PIC X(7) VALUE 'C84A0  '.
               10  FILLER           PIC X(7) VALUE 'C84A1  '.
               10  FILLER           PIC X(7) VALUE 'C84A2  '.
               10  FILLER           PIC X(7) VALUE 'C84A3  '.
               10  FILLER           PIC X(7) VALUE 'C84A4  '.
               10  FILLER           PIC X(7) VALUE 'C84A5  '.
               10  FILLER           PIC X(7) VALUE 'C84A6  '.
               10  FILLER           PIC X(7) VALUE 'C84A7  '.
               10  FILLER           PIC X(7) VALUE 'C84A8  '.
               10  FILLER           PIC X(7) VALUE 'C84A9  '.
               10  FILLER           PIC X(7) VALUE 'C84Z0  '.
               10  FILLER           PIC X(7) VALUE 'C84Z1  '.
               10  FILLER           PIC X(7) VALUE 'C84Z2  '.
               10  FILLER           PIC X(7) VALUE 'C84Z3  '.
               10  FILLER           PIC X(7) VALUE 'C84Z4  '.
               10  FILLER           PIC X(7) VALUE 'C84Z5  '.
               10  FILLER           PIC X(7) VALUE 'C84Z6  '.
               10  FILLER           PIC X(7) VALUE 'C84Z7  '.
               10  FILLER           PIC X(7) VALUE 'C84Z8  '.
               10  FILLER           PIC X(7) VALUE 'C84Z9  '.
               10  FILLER           PIC X(7) VALUE 'C8400  '.
               10  FILLER           PIC X(7) VALUE 'C8401  '.
               10  FILLER           PIC X(7) VALUE 'C8402  '.
               10  FILLER           PIC X(7) VALUE 'C8403  '.
               10  FILLER           PIC X(7) VALUE 'C8404  '.
               10  FILLER           PIC X(7) VALUE 'C8405  '.
               10  FILLER           PIC X(7) VALUE 'C8406  '.
               10  FILLER           PIC X(7) VALUE 'C8407  '.
               10  FILLER           PIC X(7) VALUE 'C8408  '.
               10  FILLER           PIC X(7) VALUE 'C8409  '.
               10  FILLER           PIC X(7) VALUE 'C8410  '.
               10  FILLER           PIC X(7) VALUE 'C8411  '.
               10  FILLER           PIC X(7) VALUE 'C8412  '.
               10  FILLER           PIC X(7) VALUE 'C8413  '.
               10  FILLER           PIC X(7) VALUE 'C8414  '.
               10  FILLER           PIC X(7) VALUE 'C8415  '.
               10  FILLER           PIC X(7) VALUE 'C8416  '.
               10  FILLER           PIC X(7) VALUE 'C8417  '.
               10  FILLER           PIC X(7) VALUE 'C8418  '.
               10  FILLER           PIC X(7) VALUE 'C8419  '.
               10  FILLER           PIC X(7) VALUE 'C8440  '.
               10  FILLER           PIC X(7) VALUE 'C8441  '.
               10  FILLER           PIC X(7) VALUE 'C8442  '.
               10  FILLER           PIC X(7) VALUE 'C8443  '.
               10  FILLER           PIC X(7) VALUE 'C8444  '.
               10  FILLER           PIC X(7) VALUE 'C8445  '.
               10  FILLER           PIC X(7) VALUE 'C8446  '.
               10  FILLER           PIC X(7) VALUE 'C8447  '.
               10  FILLER           PIC X(7) VALUE 'C8448  '.
               10  FILLER           PIC X(7) VALUE 'C8449  '.
               10  FILLER           PIC X(7) VALUE 'C8460  '.
               10  FILLER           PIC X(7) VALUE 'C8461  '.
               10  FILLER           PIC X(7) VALUE 'C8462  '.
               10  FILLER           PIC X(7) VALUE 'C8463  '.
               10  FILLER           PIC X(7) VALUE 'C8464  '.
               10  FILLER           PIC X(7) VALUE 'C8465  '.
               10  FILLER           PIC X(7) VALUE 'C8466  '.
               10  FILLER           PIC X(7) VALUE 'C8467  '.
               10  FILLER           PIC X(7) VALUE 'C8468  '.
               10  FILLER           PIC X(7) VALUE 'C8469  '.
               10  FILLER           PIC X(7) VALUE 'C847A  '.
               10  FILLER           PIC X(7) VALUE 'C8470  '.
               10  FILLER           PIC X(7) VALUE 'C8471  '.
               10  FILLER           PIC X(7) VALUE 'C8472  '.
               10  FILLER           PIC X(7) VALUE 'C8473  '.
               10  FILLER           PIC X(7) VALUE 'C8474  '.
               10  FILLER           PIC X(7) VALUE 'C8475  '.
               10  FILLER           PIC X(7) VALUE 'C8476  '.
               10  FILLER           PIC X(7) VALUE 'C8477  '.
               10  FILLER           PIC X(7) VALUE 'C8478  '.
               10  FILLER           PIC X(7) VALUE 'C8479  '.
               10  FILLER           PIC X(7) VALUE 'C8490  '.
               10  FILLER           PIC X(7) VALUE 'C8491  '.
               10  FILLER           PIC X(7) VALUE 'C8492  '.
               10  FILLER           PIC X(7) VALUE 'C8493  '.
               10  FILLER           PIC X(7) VALUE 'C8494  '.
               10  FILLER           PIC X(7) VALUE 'C8495  '.
               10  FILLER           PIC X(7) VALUE 'C8496  '.
               10  FILLER           PIC X(7) VALUE 'C8497  '.
               10  FILLER           PIC X(7) VALUE 'C8498  '.
               10  FILLER           PIC X(7) VALUE 'C8499  '.
               10  FILLER           PIC X(7) VALUE 'C8510  '.
               10  FILLER           PIC X(7) VALUE 'C8511  '.
               10  FILLER           PIC X(7) VALUE 'C8512  '.
               10  FILLER           PIC X(7) VALUE 'C8513  '.
               10  FILLER           PIC X(7) VALUE 'C8514  '.
               10  FILLER           PIC X(7) VALUE 'C8515  '.
               10  FILLER           PIC X(7) VALUE 'C8516  '.
               10  FILLER           PIC X(7) VALUE 'C8517  '.
               10  FILLER           PIC X(7) VALUE 'C8518  '.
               10  FILLER           PIC X(7) VALUE 'C8519  '.
               10  FILLER           PIC X(7) VALUE 'C8520  '.
               10  FILLER           PIC X(7) VALUE 'C8521  '.
               10  FILLER           PIC X(7) VALUE 'C8522  '.
               10  FILLER           PIC X(7) VALUE 'C8523  '.
               10  FILLER           PIC X(7) VALUE 'C8524  '.
               10  FILLER           PIC X(7) VALUE 'C8525  '.
               10  FILLER           PIC X(7) VALUE 'C8526  '.
               10  FILLER           PIC X(7) VALUE 'C8527  '.
               10  FILLER           PIC X(7) VALUE 'C8528  '.
               10  FILLER           PIC X(7) VALUE 'C8529  '.
               10  FILLER           PIC X(7) VALUE 'C8580  '.
               10  FILLER           PIC X(7) VALUE 'C8581  '.
               10  FILLER           PIC X(7) VALUE 'C8582  '.
               10  FILLER           PIC X(7) VALUE 'C8583  '.
               10  FILLER           PIC X(7) VALUE 'C8584  '.
               10  FILLER           PIC X(7) VALUE 'C8585  '.
               10  FILLER           PIC X(7) VALUE 'C8586  '.
               10  FILLER           PIC X(7) VALUE 'C8587  '.
               10  FILLER           PIC X(7) VALUE 'C8588  '.
               10  FILLER           PIC X(7) VALUE 'C8589  '.
               10  FILLER           PIC X(7) VALUE 'C8590  '.
               10  FILLER           PIC X(7) VALUE 'C8591  '.
               10  FILLER           PIC X(7) VALUE 'C8592  '.
               10  FILLER           PIC X(7) VALUE 'C8593  '.
               10  FILLER           PIC X(7) VALUE 'C8594  '.
               10  FILLER           PIC X(7) VALUE 'C8595  '.
               10  FILLER           PIC X(7) VALUE 'C8596  '.
               10  FILLER           PIC X(7) VALUE 'C8597  '.
               10  FILLER           PIC X(7) VALUE 'C8598  '.
               10  FILLER           PIC X(7) VALUE 'C8599  '.
               10  FILLER           PIC X(7) VALUE 'C860   '.
               10  FILLER           PIC X(7) VALUE 'C861   '.
               10  FILLER           PIC X(7) VALUE 'C862   '.
               10  FILLER           PIC X(7) VALUE 'C863   '.
               10  FILLER           PIC X(7) VALUE 'C864   '.
               10  FILLER           PIC X(7) VALUE 'C865   '.
               10  FILLER           PIC X(7) VALUE 'C866   '.
               10  FILLER           PIC X(7) VALUE 'C882   '.
               10  FILLER           PIC X(7) VALUE 'C883   '.
               10  FILLER           PIC X(7) VALUE 'C884   '.
               10  FILLER           PIC X(7) VALUE 'C888   '.
               10  FILLER           PIC X(7) VALUE 'C889   '.
               10  FILLER           PIC X(7) VALUE 'C9000  '.
               10  FILLER           PIC X(7) VALUE 'C9001  '.
               10  FILLER           PIC X(7) VALUE 'C9002  '.
               10  FILLER           PIC X(7) VALUE 'C9010  '.
               10  FILLER           PIC X(7) VALUE 'C9011  '.
               10  FILLER           PIC X(7) VALUE 'C9012  '.
               10  FILLER           PIC X(7) VALUE 'C9020  '.
               10  FILLER           PIC X(7) VALUE 'C9021  '.
               10  FILLER           PIC X(7) VALUE 'C9022  '.
               10  FILLER           PIC X(7) VALUE 'C9030  '.
               10  FILLER           PIC X(7) VALUE 'C9031  '.
               10  FILLER           PIC X(7) VALUE 'C9032  '.
               10  FILLER           PIC X(7) VALUE 'C91A0  '.
               10  FILLER           PIC X(7) VALUE 'C91A1  '.
               10  FILLER           PIC X(7) VALUE 'C91A2  '.
               10  FILLER           PIC X(7) VALUE 'C91Z0  '.
               10  FILLER           PIC X(7) VALUE 'C91Z1  '.
               10  FILLER           PIC X(7) VALUE 'C91Z2  '.
               10  FILLER           PIC X(7) VALUE 'C9100  '.
               10  FILLER           PIC X(7) VALUE 'C9101  '.
               10  FILLER           PIC X(7) VALUE 'C9102  '.
               10  FILLER           PIC X(7) VALUE 'C9110  '.
               10  FILLER           PIC X(7) VALUE 'C9111  '.
               10  FILLER           PIC X(7) VALUE 'C9112  '.
               10  FILLER           PIC X(7) VALUE 'C9130  '.
               10  FILLER           PIC X(7) VALUE 'C9131  '.
               10  FILLER           PIC X(7) VALUE 'C9132  '.
               10  FILLER           PIC X(7) VALUE 'C9140  '.
               10  FILLER           PIC X(7) VALUE 'C9141  '.
               10  FILLER           PIC X(7) VALUE 'C9142  '.
               10  FILLER           PIC X(7) VALUE 'C9150  '.
               10  FILLER           PIC X(7) VALUE 'C9151  '.
               10  FILLER           PIC X(7) VALUE 'C9152  '.
               10  FILLER           PIC X(7) VALUE 'C9160  '.
               10  FILLER           PIC X(7) VALUE 'C9161  '.
               10  FILLER           PIC X(7) VALUE 'C9162  '.
               10  FILLER           PIC X(7) VALUE 'C9190  '.
               10  FILLER           PIC X(7) VALUE 'C9191  '.
               10  FILLER           PIC X(7) VALUE 'C9192  '.
               10  FILLER           PIC X(7) VALUE 'C92A0  '.
               10  FILLER           PIC X(7) VALUE 'C92A1  '.
               10  FILLER           PIC X(7) VALUE 'C92A2  '.
               10  FILLER           PIC X(7) VALUE 'C92Z0  '.
               10  FILLER           PIC X(7) VALUE 'C92Z1  '.
               10  FILLER           PIC X(7) VALUE 'C92Z2  '.
               10  FILLER           PIC X(7) VALUE 'C9200  '.
               10  FILLER           PIC X(7) VALUE 'C9201  '.
               10  FILLER           PIC X(7) VALUE 'C9202  '.
               10  FILLER           PIC X(7) VALUE 'C9210  '.
               10  FILLER           PIC X(7) VALUE 'C9211  '.
               10  FILLER           PIC X(7) VALUE 'C9212  '.
               10  FILLER           PIC X(7) VALUE 'C9220  '.
               10  FILLER           PIC X(7) VALUE 'C9221  '.
               10  FILLER           PIC X(7) VALUE 'C9222  '.
               10  FILLER           PIC X(7) VALUE 'C9230  '.
               10  FILLER           PIC X(7) VALUE 'C9231  '.
               10  FILLER           PIC X(7) VALUE 'C9232  '.
               10  FILLER           PIC X(7) VALUE 'C9240  '.
               10  FILLER           PIC X(7) VALUE 'C9241  '.
               10  FILLER           PIC X(7) VALUE 'C9242  '.
               10  FILLER           PIC X(7) VALUE 'C9250  '.
               10  FILLER           PIC X(7) VALUE 'C9251  '.
               10  FILLER           PIC X(7) VALUE 'C9252  '.
               10  FILLER           PIC X(7) VALUE 'C9260  '.
               10  FILLER           PIC X(7) VALUE 'C9261  '.
               10  FILLER           PIC X(7) VALUE 'C9262  '.
               10  FILLER           PIC X(7) VALUE 'C9290  '.
               10  FILLER           PIC X(7) VALUE 'C9291  '.
               10  FILLER           PIC X(7) VALUE 'C9292  '.
               10  FILLER           PIC X(7) VALUE 'C93Z0  '.
               10  FILLER           PIC X(7) VALUE 'C93Z1  '.
               10  FILLER           PIC X(7) VALUE 'C93Z2  '.
               10  FILLER           PIC X(7) VALUE 'C9300  '.
               10  FILLER           PIC X(7) VALUE 'C9301  '.
               10  FILLER           PIC X(7) VALUE 'C9302  '.
               10  FILLER           PIC X(7) VALUE 'C9310  '.
               10  FILLER           PIC X(7) VALUE 'C9311  '.
               10  FILLER           PIC X(7) VALUE 'C9312  '.
               10  FILLER           PIC X(7) VALUE 'C9330  '.
               10  FILLER           PIC X(7) VALUE 'C9331  '.
               10  FILLER           PIC X(7) VALUE 'C9332  '.
               10  FILLER           PIC X(7) VALUE 'C9390  '.
               10  FILLER           PIC X(7) VALUE 'C9391  '.
               10  FILLER           PIC X(7) VALUE 'C9392  '.
               10  FILLER           PIC X(7) VALUE 'C9400  '.
               10  FILLER           PIC X(7) VALUE 'C9401  '.
               10  FILLER           PIC X(7) VALUE 'C9402  '.
               10  FILLER           PIC X(7) VALUE 'C9420  '.
               10  FILLER           PIC X(7) VALUE 'C9421  '.
               10  FILLER           PIC X(7) VALUE 'C9422  '.
               10  FILLER           PIC X(7) VALUE 'C9430  '.
               10  FILLER           PIC X(7) VALUE 'C9431  '.
               10  FILLER           PIC X(7) VALUE 'C9432  '.
               10  FILLER           PIC X(7) VALUE 'C9440  '.
               10  FILLER           PIC X(7) VALUE 'C9441  '.
               10  FILLER           PIC X(7) VALUE 'C9442  '.
               10  FILLER           PIC X(7) VALUE 'C946   '.
               10  FILLER           PIC X(7) VALUE 'C9480  '.
               10  FILLER           PIC X(7) VALUE 'C9481  '.
               10  FILLER           PIC X(7) VALUE 'C9482  '.
               10  FILLER           PIC X(7) VALUE 'C9500  '.
               10  FILLER           PIC X(7) VALUE 'C9501  '.
               10  FILLER           PIC X(7) VALUE 'C9502  '.
               10  FILLER           PIC X(7) VALUE 'C9510  '.
               10  FILLER           PIC X(7) VALUE 'C9511  '.
               10  FILLER           PIC X(7) VALUE 'C9512  '.
               10  FILLER           PIC X(7) VALUE 'C9590  '.
               10  FILLER           PIC X(7) VALUE 'C9591  '.
               10  FILLER           PIC X(7) VALUE 'C9592  '.
               10  FILLER           PIC X(7) VALUE 'C96A   '.
               10  FILLER           PIC X(7) VALUE 'C96Z   '.
               10  FILLER           PIC X(7) VALUE 'C960   '.
               10  FILLER           PIC X(7) VALUE 'C9620  '.
               10  FILLER           PIC X(7) VALUE 'C9621  '.
               10  FILLER           PIC X(7) VALUE 'C9622  '.
               10  FILLER           PIC X(7) VALUE 'C9629  '.
               10  FILLER           PIC X(7) VALUE 'C964   '.
               10  FILLER           PIC X(7) VALUE 'C969   '.
               10  FILLER           PIC X(7) VALUE 'D0000  '.
               10  FILLER           PIC X(7) VALUE 'D0001  '.
               10  FILLER           PIC X(7) VALUE 'D0002  '.
               10  FILLER           PIC X(7) VALUE 'D0003  '.
               10  FILLER           PIC X(7) VALUE 'D0004  '.
               10  FILLER           PIC X(7) VALUE 'D0005  '.
               10  FILLER           PIC X(7) VALUE 'D0006  '.
               10  FILLER           PIC X(7) VALUE 'D0007  '.
               10  FILLER           PIC X(7) VALUE 'D0008  '.
               10  FILLER           PIC X(7) VALUE 'D001   '.
               10  FILLER           PIC X(7) VALUE 'D002   '.
               10  FILLER           PIC X(7) VALUE 'D010   '.
               10  FILLER           PIC X(7) VALUE 'D011   '.
               10  FILLER           PIC X(7) VALUE 'D012   '.
               10  FILLER           PIC X(7) VALUE 'D013   '.
               10  FILLER           PIC X(7) VALUE 'D0140  '.
               10  FILLER           PIC X(7) VALUE 'D0149  '.
               10  FILLER           PIC X(7) VALUE 'D015   '.
               10  FILLER           PIC X(7) VALUE 'D017   '.
               10  FILLER           PIC X(7) VALUE 'D019   '.
               10  FILLER           PIC X(7) VALUE 'D020   '.
               10  FILLER           PIC X(7) VALUE 'D021   '.
               10  FILLER           PIC X(7) VALUE 'D0220  '.
               10  FILLER           PIC X(7) VALUE 'D0221  '.
               10  FILLER           PIC X(7) VALUE 'D0222  '.
               10  FILLER           PIC X(7) VALUE 'D023   '.
               10  FILLER           PIC X(7) VALUE 'D024   '.
               10  FILLER           PIC X(7) VALUE 'D030   '.
               10  FILLER           PIC X(7) VALUE 'D03111 '.
               10  FILLER           PIC X(7) VALUE 'D03112 '.
               10  FILLER           PIC X(7) VALUE 'D03121 '.
               10  FILLER           PIC X(7) VALUE 'D03122 '.
               10  FILLER           PIC X(7) VALUE 'D0321  '.
               10  FILLER           PIC X(7) VALUE 'D0322  '.
               10  FILLER           PIC X(7) VALUE 'D0330  '.
               10  FILLER           PIC X(7) VALUE 'D0339  '.
               10  FILLER           PIC X(7) VALUE 'D034   '.
               10  FILLER           PIC X(7) VALUE 'D0351  '.
               10  FILLER           PIC X(7) VALUE 'D0352  '.
               10  FILLER           PIC X(7) VALUE 'D0359  '.
               10  FILLER           PIC X(7) VALUE 'D0361  '.
               10  FILLER           PIC X(7) VALUE 'D0362  '.
               10  FILLER           PIC X(7) VALUE 'D0371  '.
               10  FILLER           PIC X(7) VALUE 'D0372  '.
               10  FILLER           PIC X(7) VALUE 'D038   '.
               10  FILLER           PIC X(7) VALUE 'D039   '.
               10  FILLER           PIC X(7) VALUE 'D040   '.
               10  FILLER           PIC X(7) VALUE 'D04111 '.
               10  FILLER           PIC X(7) VALUE 'D04112 '.
               10  FILLER           PIC X(7) VALUE 'D04121 '.
               10  FILLER           PIC X(7) VALUE 'D04122 '.
               10  FILLER           PIC X(7) VALUE 'D0421  '.
               10  FILLER           PIC X(7) VALUE 'D0422  '.
               10  FILLER           PIC X(7) VALUE 'D0430  '.
               10  FILLER           PIC X(7) VALUE 'D0439  '.
               10  FILLER           PIC X(7) VALUE 'D044   '.
               10  FILLER           PIC X(7) VALUE 'D045   '.
               10  FILLER           PIC X(7) VALUE 'D0461  '.
               10  FILLER           PIC X(7) VALUE 'D0462  '.
               10  FILLER           PIC X(7) VALUE 'D0471  '.
               10  FILLER           PIC X(7) VALUE 'D0472  '.
               10  FILLER           PIC X(7) VALUE 'D048   '.
               10  FILLER           PIC X(7) VALUE 'D049   '.
               10  FILLER           PIC X(7) VALUE 'D0501  '.
               10  FILLER           PIC X(7) VALUE 'D0502  '.
               10  FILLER           PIC X(7) VALUE 'D0511  '.
               10  FILLER           PIC X(7) VALUE 'D0512  '.
               10  FILLER           PIC X(7) VALUE 'D0581  '.
               10  FILLER           PIC X(7) VALUE 'D0582  '.
               10  FILLER           PIC X(7) VALUE 'D0591  '.
               10  FILLER           PIC X(7) VALUE 'D0592  '.
               10  FILLER           PIC X(7) VALUE 'D060   '.
               10  FILLER           PIC X(7) VALUE 'D061   '.
               10  FILLER           PIC X(7) VALUE 'D067   '.
               10  FILLER           PIC X(7) VALUE 'D069   '.
               10  FILLER           PIC X(7) VALUE 'D070   '.
               10  FILLER           PIC X(7) VALUE 'D071   '.
               10  FILLER           PIC X(7) VALUE 'D072   '.
               10  FILLER           PIC X(7) VALUE 'D0730  '.
               10  FILLER           PIC X(7) VALUE 'D0739  '.
               10  FILLER           PIC X(7) VALUE 'D074   '.
               10  FILLER           PIC X(7) VALUE 'D075   '.
               10  FILLER           PIC X(7) VALUE 'D0760  '.
               10  FILLER           PIC X(7) VALUE 'D0761  '.
               10  FILLER           PIC X(7) VALUE 'D0769  '.
               10  FILLER           PIC X(7) VALUE 'D090   '.
               10  FILLER           PIC X(7) VALUE 'D0910  '.
               10  FILLER           PIC X(7) VALUE 'D0919  '.
               10  FILLER           PIC X(7) VALUE 'D0921  '.
               10  FILLER           PIC X(7) VALUE 'D0922  '.
               10  FILLER           PIC X(7) VALUE 'D093   '.
               10  FILLER           PIC X(7) VALUE 'D098   '.
               10  FILLER           PIC X(7) VALUE 'D099   '.
               10  FILLER           PIC X(7) VALUE 'D100   '.
               10  FILLER           PIC X(7) VALUE 'D101   '.
               10  FILLER           PIC X(7) VALUE 'D102   '.
               10  FILLER           PIC X(7) VALUE 'D1030  '.
               10  FILLER           PIC X(7) VALUE 'D1039  '.
               10  FILLER           PIC X(7) VALUE 'D104   '.
               10  FILLER           PIC X(7) VALUE 'D105   '.
               10  FILLER           PIC X(7) VALUE 'D106   '.
               10  FILLER           PIC X(7) VALUE 'D107   '.
               10  FILLER           PIC X(7) VALUE 'D109   '.
               10  FILLER           PIC X(7) VALUE 'D110   '.
               10  FILLER           PIC X(7) VALUE 'D117   '.
               10  FILLER           PIC X(7) VALUE 'D119   '.
               10  FILLER           PIC X(7) VALUE 'D120   '.
               10  FILLER           PIC X(7) VALUE 'D121   '.
               10  FILLER           PIC X(7) VALUE 'D122   '.
               10  FILLER           PIC X(7) VALUE 'D123   '.
               10  FILLER           PIC X(7) VALUE 'D124   '.
               10  FILLER           PIC X(7) VALUE 'D125   '.
               10  FILLER           PIC X(7) VALUE 'D126   '.
               10  FILLER           PIC X(7) VALUE 'D127   '.
               10  FILLER           PIC X(7) VALUE 'D128   '.
               10  FILLER           PIC X(7) VALUE 'D129   '.
               10  FILLER           PIC X(7) VALUE 'D130   '.
               10  FILLER           PIC X(7) VALUE 'D131   '.
               10  FILLER           PIC X(7) VALUE 'D132   '.
               10  FILLER           PIC X(7) VALUE 'D1330  '.
               10  FILLER           PIC X(7) VALUE 'D1339  '.
               10  FILLER           PIC X(7) VALUE 'D134   '.
               10  FILLER           PIC X(7) VALUE 'D135   '.
               10  FILLER           PIC X(7) VALUE 'D136   '.
               10  FILLER           PIC X(7) VALUE 'D137   '.
               10  FILLER           PIC X(7) VALUE 'D139   '.
               10  FILLER           PIC X(7) VALUE 'D140   '.
               10  FILLER           PIC X(7) VALUE 'D141   '.
               10  FILLER           PIC X(7) VALUE 'D142   '.
               10  FILLER           PIC X(7) VALUE 'D1430  '.
               10  FILLER           PIC X(7) VALUE 'D1431  '.
               10  FILLER           PIC X(7) VALUE 'D1432  '.
               10  FILLER           PIC X(7) VALUE 'D144   '.
               10  FILLER           PIC X(7) VALUE 'D150   '.
               10  FILLER           PIC X(7) VALUE 'D151   '.
               10  FILLER           PIC X(7) VALUE 'D152   '.
               10  FILLER           PIC X(7) VALUE 'D157   '.
               10  FILLER           PIC X(7) VALUE 'D159   '.
               10  FILLER           PIC X(7) VALUE 'D1601  '.
               10  FILLER           PIC X(7) VALUE 'D1602  '.
               10  FILLER           PIC X(7) VALUE 'D1611  '.
               10  FILLER           PIC X(7) VALUE 'D1612  '.
               10  FILLER           PIC X(7) VALUE 'D1621  '.
               10  FILLER           PIC X(7) VALUE 'D1622  '.
               10  FILLER           PIC X(7) VALUE 'D1631  '.
               10  FILLER           PIC X(7) VALUE 'D1632  '.
               10  FILLER           PIC X(7) VALUE 'D164   '.
               10  FILLER           PIC X(7) VALUE 'D165   '.
               10  FILLER           PIC X(7) VALUE 'D166   '.
               10  FILLER           PIC X(7) VALUE 'D167   '.
               10  FILLER           PIC X(7) VALUE 'D168   '.
               10  FILLER           PIC X(7) VALUE 'D169   '.
               10  FILLER           PIC X(7) VALUE 'D170   '.
               10  FILLER           PIC X(7) VALUE 'D171   '.
               10  FILLER           PIC X(7) VALUE 'D1721  '.
               10  FILLER           PIC X(7) VALUE 'D1722  '.
               10  FILLER           PIC X(7) VALUE 'D1723  '.
               10  FILLER           PIC X(7) VALUE 'D1724  '.
               10  FILLER           PIC X(7) VALUE 'D1730  '.
               10  FILLER           PIC X(7) VALUE 'D1739  '.
               10  FILLER           PIC X(7) VALUE 'D174   '.
               10  FILLER           PIC X(7) VALUE 'D175   '.
               10  FILLER           PIC X(7) VALUE 'D176   '.
               10  FILLER           PIC X(7) VALUE 'D1771  '.
               10  FILLER           PIC X(7) VALUE 'D1772  '.
               10  FILLER           PIC X(7) VALUE 'D1779  '.
               10  FILLER           PIC X(7) VALUE 'D179   '.
               10  FILLER           PIC X(7) VALUE 'D1800  '.
               10  FILLER           PIC X(7) VALUE 'D1801  '.
               10  FILLER           PIC X(7) VALUE 'D1802  '.
               10  FILLER           PIC X(7) VALUE 'D1803  '.
               10  FILLER           PIC X(7) VALUE 'D1809  '.
               10  FILLER           PIC X(7) VALUE 'D181   '.
               10  FILLER           PIC X(7) VALUE 'D190   '.
               10  FILLER           PIC X(7) VALUE 'D191   '.
               10  FILLER           PIC X(7) VALUE 'D197   '.
               10  FILLER           PIC X(7) VALUE 'D199   '.
               10  FILLER           PIC X(7) VALUE 'D200   '.
               10  FILLER           PIC X(7) VALUE 'D201   '.
               10  FILLER           PIC X(7) VALUE 'D210   '.
               10  FILLER           PIC X(7) VALUE 'D2111  '.
               10  FILLER           PIC X(7) VALUE 'D2112  '.
               10  FILLER           PIC X(7) VALUE 'D2121  '.
               10  FILLER           PIC X(7) VALUE 'D2122  '.
               10  FILLER           PIC X(7) VALUE 'D213   '.
               10  FILLER           PIC X(7) VALUE 'D214   '.
               10  FILLER           PIC X(7) VALUE 'D215   '.
               10  FILLER           PIC X(7) VALUE 'D216   '.
               10  FILLER           PIC X(7) VALUE 'D219   '.
               10  FILLER           PIC X(7) VALUE 'D220   '.
               10  FILLER           PIC X(7) VALUE 'D22111 '.
               10  FILLER           PIC X(7) VALUE 'D22112 '.
               10  FILLER           PIC X(7) VALUE 'D22121 '.
               10  FILLER           PIC X(7) VALUE 'D22122 '.
               10  FILLER           PIC X(7) VALUE 'D2221  '.
               10  FILLER           PIC X(7) VALUE 'D2222  '.
               10  FILLER           PIC X(7) VALUE 'D2230  '.
               10  FILLER           PIC X(7) VALUE 'D2239  '.
               10  FILLER           PIC X(7) VALUE 'D224   '.
               10  FILLER           PIC X(7) VALUE 'D225   '.
               10  FILLER           PIC X(7) VALUE 'D2261  '.
               10  FILLER           PIC X(7) VALUE 'D2262  '.
               10  FILLER           PIC X(7) VALUE 'D2271  '.
               10  FILLER           PIC X(7) VALUE 'D2272  '.
               10  FILLER           PIC X(7) VALUE 'D229   '.
               10  FILLER           PIC X(7) VALUE 'D230   '.
               10  FILLER           PIC X(7) VALUE 'D23111 '.
               10  FILLER           PIC X(7) VALUE 'D23112 '.
               10  FILLER           PIC X(7) VALUE 'D23121 '.
               10  FILLER           PIC X(7) VALUE 'D23122 '.
               10  FILLER           PIC X(7) VALUE 'D2321  '.
               10  FILLER           PIC X(7) VALUE 'D2322  '.
               10  FILLER           PIC X(7) VALUE 'D2330  '.
               10  FILLER           PIC X(7) VALUE 'D2339  '.
               10  FILLER           PIC X(7) VALUE 'D234   '.
               10  FILLER           PIC X(7) VALUE 'D235   '.
               10  FILLER           PIC X(7) VALUE 'D2361  '.
               10  FILLER           PIC X(7) VALUE 'D2362  '.
               10  FILLER           PIC X(7) VALUE 'D2371  '.
               10  FILLER           PIC X(7) VALUE 'D2372  '.
               10  FILLER           PIC X(7) VALUE 'D239   '.
               10  FILLER           PIC X(7) VALUE 'D241   '.
               10  FILLER           PIC X(7) VALUE 'D242   '.
               10  FILLER           PIC X(7) VALUE 'D250   '.
               10  FILLER           PIC X(7) VALUE 'D251   '.
               10  FILLER           PIC X(7) VALUE 'D252   '.
               10  FILLER           PIC X(7) VALUE 'D259   '.
               10  FILLER           PIC X(7) VALUE 'D260   '.
               10  FILLER           PIC X(7) VALUE 'D261   '.
               10  FILLER           PIC X(7) VALUE 'D267   '.
               10  FILLER           PIC X(7) VALUE 'D269   '.
               10  FILLER           PIC X(7) VALUE 'D270   '.
               10  FILLER           PIC X(7) VALUE 'D271   '.
               10  FILLER           PIC X(7) VALUE 'D279   '.
               10  FILLER           PIC X(7) VALUE 'D280   '.
               10  FILLER           PIC X(7) VALUE 'D281   '.
               10  FILLER           PIC X(7) VALUE 'D282   '.
               10  FILLER           PIC X(7) VALUE 'D287   '.
               10  FILLER           PIC X(7) VALUE 'D289   '.
               10  FILLER           PIC X(7) VALUE 'D290   '.
               10  FILLER           PIC X(7) VALUE 'D291   '.
               10  FILLER           PIC X(7) VALUE 'D2920  '.
               10  FILLER           PIC X(7) VALUE 'D2921  '.
               10  FILLER           PIC X(7) VALUE 'D2922  '.
               10  FILLER           PIC X(7) VALUE 'D2930  '.
               10  FILLER           PIC X(7) VALUE 'D2931  '.
               10  FILLER           PIC X(7) VALUE 'D2932  '.
               10  FILLER           PIC X(7) VALUE 'D294   '.
               10  FILLER           PIC X(7) VALUE 'D298   '.
               10  FILLER           PIC X(7) VALUE 'D299   '.
               10  FILLER           PIC X(7) VALUE 'D3A00  '.
               10  FILLER           PIC X(7) VALUE 'D3A010 '.
               10  FILLER           PIC X(7) VALUE 'D3A011 '.
               10  FILLER           PIC X(7) VALUE 'D3A012 '.
               10  FILLER           PIC X(7) VALUE 'D3A019 '.
               10  FILLER           PIC X(7) VALUE 'D3A020 '.
               10  FILLER           PIC X(7) VALUE 'D3A021 '.
               10  FILLER           PIC X(7) VALUE 'D3A022 '.
               10  FILLER           PIC X(7) VALUE 'D3A023 '.
               10  FILLER           PIC X(7) VALUE 'D3A024 '.
               10  FILLER           PIC X(7) VALUE 'D3A025 '.
               10  FILLER           PIC X(7) VALUE 'D3A026 '.
               10  FILLER           PIC X(7) VALUE 'D3A029 '.
               10  FILLER           PIC X(7) VALUE 'D3A090 '.
               10  FILLER           PIC X(7) VALUE 'D3A091 '.
               10  FILLER           PIC X(7) VALUE 'D3A092 '.
               10  FILLER           PIC X(7) VALUE 'D3A093 '.
               10  FILLER           PIC X(7) VALUE 'D3A094 '.
               10  FILLER           PIC X(7) VALUE 'D3A095 '.
               10  FILLER           PIC X(7) VALUE 'D3A096 '.
               10  FILLER           PIC X(7) VALUE 'D3A098 '.
               10  FILLER           PIC X(7) VALUE 'D3A8   '.
               10  FILLER           PIC X(7) VALUE 'D3000  '.
               10  FILLER           PIC X(7) VALUE 'D3001  '.
               10  FILLER           PIC X(7) VALUE 'D3002  '.
               10  FILLER           PIC X(7) VALUE 'D3010  '.
               10  FILLER           PIC X(7) VALUE 'D3011  '.
               10  FILLER           PIC X(7) VALUE 'D3012  '.
               10  FILLER           PIC X(7) VALUE 'D3020  '.
               10  FILLER           PIC X(7) VALUE 'D3021  '.
               10  FILLER           PIC X(7) VALUE 'D3022  '.
               10  FILLER           PIC X(7) VALUE 'D303   '.
               10  FILLER           PIC X(7) VALUE 'D304   '.
               10  FILLER           PIC X(7) VALUE 'D308   '.
               10  FILLER           PIC X(7) VALUE 'D309   '.
               10  FILLER           PIC X(7) VALUE 'D3101  '.
               10  FILLER           PIC X(7) VALUE 'D3102  '.
               10  FILLER           PIC X(7) VALUE 'D3110  '.
               10  FILLER           PIC X(7) VALUE 'D3111  '.
               10  FILLER           PIC X(7) VALUE 'D3112  '.
               10  FILLER           PIC X(7) VALUE 'D3120  '.
               10  FILLER           PIC X(7) VALUE 'D3121  '.
               10  FILLER           PIC X(7) VALUE 'D3122  '.
               10  FILLER           PIC X(7) VALUE 'D3130  '.
               10  FILLER           PIC X(7) VALUE 'D3131  '.
               10  FILLER           PIC X(7) VALUE 'D3132  '.
               10  FILLER           PIC X(7) VALUE 'D3140  '.
               10  FILLER           PIC X(7) VALUE 'D3141  '.
               10  FILLER           PIC X(7) VALUE 'D3142  '.
               10  FILLER           PIC X(7) VALUE 'D3151  '.
               10  FILLER           PIC X(7) VALUE 'D3152  '.
               10  FILLER           PIC X(7) VALUE 'D3161  '.
               10  FILLER           PIC X(7) VALUE 'D3162  '.
               10  FILLER           PIC X(7) VALUE 'D3191  '.
               10  FILLER           PIC X(7) VALUE 'D3192  '.
               10  FILLER           PIC X(7) VALUE 'D320   '.
               10  FILLER           PIC X(7) VALUE 'D321   '.
               10  FILLER           PIC X(7) VALUE 'D329   '.
               10  FILLER           PIC X(7) VALUE 'D330   '.
               10  FILLER           PIC X(7) VALUE 'D331   '.
               10  FILLER           PIC X(7) VALUE 'D332   '.
               10  FILLER           PIC X(7) VALUE 'D333   '.
               10  FILLER           PIC X(7) VALUE 'D334   '.
               10  FILLER           PIC X(7) VALUE 'D337   '.
               10  FILLER           PIC X(7) VALUE 'D339   '.
               10  FILLER           PIC X(7) VALUE 'D34    '.
               10  FILLER           PIC X(7) VALUE 'D3500  '.
               10  FILLER           PIC X(7) VALUE 'D3501  '.
               10  FILLER           PIC X(7) VALUE 'D3502  '.
               10  FILLER           PIC X(7) VALUE 'D351   '.
               10  FILLER           PIC X(7) VALUE 'D352   '.
               10  FILLER           PIC X(7) VALUE 'D353   '.
               10  FILLER           PIC X(7) VALUE 'D354   '.
               10  FILLER           PIC X(7) VALUE 'D355   '.
               10  FILLER           PIC X(7) VALUE 'D356   '.
               10  FILLER           PIC X(7) VALUE 'D357   '.
               10  FILLER           PIC X(7) VALUE 'D359   '.
               10  FILLER           PIC X(7) VALUE 'D360   '.
               10  FILLER           PIC X(7) VALUE 'D3610  '.
               10  FILLER           PIC X(7) VALUE 'D3611  '.
               10  FILLER           PIC X(7) VALUE 'D3612  '.
               10  FILLER           PIC X(7) VALUE 'D3613  '.
               10  FILLER           PIC X(7) VALUE 'D3614  '.
               10  FILLER           PIC X(7) VALUE 'D3615  '.
               10  FILLER           PIC X(7) VALUE 'D3616  '.
               10  FILLER           PIC X(7) VALUE 'D3617  '.
               10  FILLER           PIC X(7) VALUE 'D367   '.
               10  FILLER           PIC X(7) VALUE 'D369   '.
               10  FILLER           PIC X(7) VALUE 'D3701  '.
               10  FILLER           PIC X(7) VALUE 'D3702  '.
               10  FILLER           PIC X(7) VALUE 'D37030 '.
               10  FILLER           PIC X(7) VALUE 'D37031 '.
               10  FILLER           PIC X(7) VALUE 'D37032 '.
               10  FILLER           PIC X(7) VALUE 'D37039 '.
               10  FILLER           PIC X(7) VALUE 'D3704  '.
               10  FILLER           PIC X(7) VALUE 'D3705  '.
               10  FILLER           PIC X(7) VALUE 'D3709  '.
               10  FILLER           PIC X(7) VALUE 'D371   '.
               10  FILLER           PIC X(7) VALUE 'D372   '.
               10  FILLER           PIC X(7) VALUE 'D373   '.
               10  FILLER           PIC X(7) VALUE 'D374   '.
               10  FILLER           PIC X(7) VALUE 'D375   '.
               10  FILLER           PIC X(7) VALUE 'D376   '.
               10  FILLER           PIC X(7) VALUE 'D378   '.
               10  FILLER           PIC X(7) VALUE 'D379   '.
               10  FILLER           PIC X(7) VALUE 'D380   '.
               10  FILLER           PIC X(7) VALUE 'D381   '.
               10  FILLER           PIC X(7) VALUE 'D382   '.
               10  FILLER           PIC X(7) VALUE 'D383   '.
               10  FILLER           PIC X(7) VALUE 'D384   '.
               10  FILLER           PIC X(7) VALUE 'D385   '.
               10  FILLER           PIC X(7) VALUE 'D386   '.
               10  FILLER           PIC X(7) VALUE 'D390   '.
               10  FILLER           PIC X(7) VALUE 'D3910  '.
               10  FILLER           PIC X(7) VALUE 'D3911  '.
               10  FILLER           PIC X(7) VALUE 'D3912  '.
               10  FILLER           PIC X(7) VALUE 'D392   '.
               10  FILLER           PIC X(7) VALUE 'D398   '.
               10  FILLER           PIC X(7) VALUE 'D399   '.
               10  FILLER           PIC X(7) VALUE 'D400   '.
               10  FILLER           PIC X(7) VALUE 'D4010  '.
               10  FILLER           PIC X(7) VALUE 'D4011  '.
               10  FILLER           PIC X(7) VALUE 'D4012  '.
               10  FILLER           PIC X(7) VALUE 'D408   '.
               10  FILLER           PIC X(7) VALUE 'D409   '.
               10  FILLER           PIC X(7) VALUE 'D4100  '.
               10  FILLER           PIC X(7) VALUE 'D4101  '.
               10  FILLER           PIC X(7) VALUE 'D4102  '.
               10  FILLER           PIC X(7) VALUE 'D4110  '.
               10  FILLER           PIC X(7) VALUE 'D4111  '.
               10  FILLER           PIC X(7) VALUE 'D4112  '.
               10  FILLER           PIC X(7) VALUE 'D4120  '.
               10  FILLER           PIC X(7) VALUE 'D4121  '.
               10  FILLER           PIC X(7) VALUE 'D4122  '.
               10  FILLER           PIC X(7) VALUE 'D413   '.
               10  FILLER           PIC X(7) VALUE 'D414   '.
               10  FILLER           PIC X(7) VALUE 'D418   '.
               10  FILLER           PIC X(7) VALUE 'D419   '.
               10  FILLER           PIC X(7) VALUE 'D420   '.
               10  FILLER           PIC X(7) VALUE 'D421   '.
               10  FILLER           PIC X(7) VALUE 'D429   '.
               10  FILLER           PIC X(7) VALUE 'D430   '.
               10  FILLER           PIC X(7) VALUE 'D431   '.
               10  FILLER           PIC X(7) VALUE 'D432   '.
               10  FILLER           PIC X(7) VALUE 'D433   '.
               10  FILLER           PIC X(7) VALUE 'D434   '.
               10  FILLER           PIC X(7) VALUE 'D438   '.
               10  FILLER           PIC X(7) VALUE 'D439   '.
               10  FILLER           PIC X(7) VALUE 'D440   '.
               10  FILLER           PIC X(7) VALUE 'D4410  '.
               10  FILLER           PIC X(7) VALUE 'D4411  '.
               10  FILLER           PIC X(7) VALUE 'D4412  '.
               10  FILLER           PIC X(7) VALUE 'D442   '.
               10  FILLER           PIC X(7) VALUE 'D443   '.
               10  FILLER           PIC X(7) VALUE 'D444   '.
               10  FILLER           PIC X(7) VALUE 'D445   '.
               10  FILLER           PIC X(7) VALUE 'D446   '.
               10  FILLER           PIC X(7) VALUE 'D447   '.
               10  FILLER           PIC X(7) VALUE 'D449   '.
               10  FILLER           PIC X(7) VALUE 'D45    '.
               10  FILLER           PIC X(7) VALUE 'D46A   '.
               10  FILLER           PIC X(7) VALUE 'D46B   '.
               10  FILLER           PIC X(7) VALUE 'D46C   '.
               10  FILLER           PIC X(7) VALUE 'D46Z   '.
               10  FILLER           PIC X(7) VALUE 'D460   '.
               10  FILLER           PIC X(7) VALUE 'D461   '.
               10  FILLER           PIC X(7) VALUE 'D4620  '.
               10  FILLER           PIC X(7) VALUE 'D4621  '.
               10  FILLER           PIC X(7) VALUE 'D4622  '.
               10  FILLER           PIC X(7) VALUE 'D464   '.
               10  FILLER           PIC X(7) VALUE 'D469   '.
               10  FILLER           PIC X(7) VALUE 'D47Z1  '.
               10  FILLER           PIC X(7) VALUE 'D47Z9  '.
               10  FILLER           PIC X(7) VALUE 'D4701  '.
               10  FILLER           PIC X(7) VALUE 'D4702  '.
               10  FILLER           PIC X(7) VALUE 'D4709  '.
               10  FILLER           PIC X(7) VALUE 'D471   '.
               10  FILLER           PIC X(7) VALUE 'D473   '.
               10  FILLER           PIC X(7) VALUE 'D479   '.
               10  FILLER           PIC X(7) VALUE 'D480   '.
               10  FILLER           PIC X(7) VALUE 'D481   '.
               10  FILLER           PIC X(7) VALUE 'D482   '.
               10  FILLER           PIC X(7) VALUE 'D483   '.
               10  FILLER           PIC X(7) VALUE 'D484   '.
               10  FILLER           PIC X(7) VALUE 'D485   '.
               10  FILLER           PIC X(7) VALUE 'D4861  '.
               10  FILLER           PIC X(7) VALUE 'D4862  '.
               10  FILLER           PIC X(7) VALUE 'D487   '.
               10  FILLER           PIC X(7) VALUE 'D489   '.
               10  FILLER           PIC X(7) VALUE 'D490   '.
               10  FILLER           PIC X(7) VALUE 'D491   '.
               10  FILLER           PIC X(7) VALUE 'D492   '.
               10  FILLER           PIC X(7) VALUE 'D493   '.
               10  FILLER           PIC X(7) VALUE 'D494   '.
               10  FILLER           PIC X(7) VALUE 'D49511 '.
               10  FILLER           PIC X(7) VALUE 'D49512 '.
               10  FILLER           PIC X(7) VALUE 'D4959  '.
               10  FILLER           PIC X(7) VALUE 'D496   '.
               10  FILLER           PIC X(7) VALUE 'D497   '.
               10  FILLER           PIC X(7) VALUE 'D4981  '.
               10  FILLER           PIC X(7) VALUE 'D4989  '.
               10  FILLER           PIC X(7) VALUE 'D499   '.
               10  FILLER           PIC X(7) VALUE 'K317   '.
               10  FILLER           PIC X(7) VALUE 'K635   '.
               10  FILLER           PIC X(7) VALUE 'Q8500  '.
               10  FILLER           PIC X(7) VALUE 'Q8501  '.
               10  FILLER           PIC X(7) VALUE 'Q8502  '.
               10  FILLER           PIC X(7) VALUE 'Q8503  '.
               10  FILLER           PIC X(7) VALUE 'Q8509  '.
           05  FILLER     REDEFINES CAT6-TABLE-DATA.
               10  CAT6-DATA        OCCURS 1544 TIMES
                                    ASCENDING KEY IS CAT6-CODE
                                    INDEXED BY IX-CAT6.
                   15  CAT6-CODE    PIC X(7).

      ****************************************************************
      * ONCOLOGY TREATMENT - PROCEDURE CODES                         *
      ****************************************************************
       01 CAT6P-TABLE.
           05  CAT6P-TABLE-DATA.
               10  FILLER           PIC X(7) VALUE 'CW7GGZZ'.
               10  FILLER           PIC X(7) VALUE 'CW7GYZZ'.
               10  FILLER           PIC X(7) VALUE 'CW7NGZZ'.
               10  FILLER           PIC X(7) VALUE 'CW7NNZZ'.
               10  FILLER           PIC X(7) VALUE 'CW7NPZZ'.
               10  FILLER           PIC X(7) VALUE 'CW7NYZZ'.
               10  FILLER           PIC X(7) VALUE 'CW7N8ZZ'.
               10  FILLER           PIC X(7) VALUE 'CW7YYZZ'.
               10  FILLER           PIC X(7) VALUE 'CW70NZZ'.
               10  FILLER           PIC X(7) VALUE 'CW70YZZ'.
               10  FILLER           PIC X(7) VALUE 'CW73NZZ'.
               10  FILLER           PIC X(7) VALUE 'CW73YZZ'.
               10  FILLER           PIC X(7) VALUE 'DBY0FZZ'.
               10  FILLER           PIC X(7) VALUE 'DBY0KZZ'.
               10  FILLER           PIC X(7) VALUE 'DBY07ZZ'.
               10  FILLER           PIC X(7) VALUE 'DBY08ZZ'.
               10  FILLER           PIC X(7) VALUE 'DBY1FZZ'.
               10  FILLER           PIC X(7) VALUE 'DBY1KZZ'.
               10  FILLER           PIC X(7) VALUE 'DBY17ZZ'.
               10  FILLER           PIC X(7) VALUE 'DBY18ZZ'.
               10  FILLER           PIC X(7) VALUE 'DBY2FZZ'.
               10  FILLER           PIC X(7) VALUE 'DBY2KZZ'.
               10  FILLER           PIC X(7) VALUE 'DBY27ZZ'.
               10  FILLER           PIC X(7) VALUE 'DBY28ZZ'.
               10  FILLER           PIC X(7) VALUE 'DBY5FZZ'.
               10  FILLER           PIC X(7) VALUE 'DBY5KZZ'.
               10  FILLER           PIC X(7) VALUE 'DBY57ZZ'.
               10  FILLER           PIC X(7) VALUE 'DBY58ZZ'.
               10  FILLER           PIC X(7) VALUE 'DBY6FZZ'.
               10  FILLER           PIC X(7) VALUE 'DBY6KZZ'.
               10  FILLER           PIC X(7) VALUE 'DBY67ZZ'.
               10  FILLER           PIC X(7) VALUE 'DBY68ZZ'.
               10  FILLER           PIC X(7) VALUE 'DBY7FZZ'.
               10  FILLER           PIC X(7) VALUE 'DBY7KZZ'.
               10  FILLER           PIC X(7) VALUE 'DBY77ZZ'.
               10  FILLER           PIC X(7) VALUE 'DBY78ZZ'.
               10  FILLER           PIC X(7) VALUE 'DBY8FZZ'.
               10  FILLER           PIC X(7) VALUE 'DBY8KZZ'.
               10  FILLER           PIC X(7) VALUE 'DBY87ZZ'.
               10  FILLER           PIC X(7) VALUE 'DBY88ZZ'.
               10  FILLER           PIC X(7) VALUE 'DB000ZZ'.
               10  FILLER           PIC X(7) VALUE 'DB001ZZ'.
               10  FILLER           PIC X(7) VALUE 'DB002ZZ'.
               10  FILLER           PIC X(7) VALUE 'DB003ZZ'.
               10  FILLER           PIC X(7) VALUE 'DB003Z0'.
               10  FILLER           PIC X(7) VALUE 'DB004ZZ'.
               10  FILLER           PIC X(7) VALUE 'DB005ZZ'.
               10  FILLER           PIC X(7) VALUE 'DB006ZZ'.
               10  FILLER           PIC X(7) VALUE 'DB010ZZ'.
               10  FILLER           PIC X(7) VALUE 'DB011ZZ'.
               10  FILLER           PIC X(7) VALUE 'DB012ZZ'.
               10  FILLER           PIC X(7) VALUE 'DB013ZZ'.
               10  FILLER           PIC X(7) VALUE 'DB013Z0'.
               10  FILLER           PIC X(7) VALUE 'DB014ZZ'.
               10  FILLER           PIC X(7) VALUE 'DB015ZZ'.
               10  FILLER           PIC X(7) VALUE 'DB016ZZ'.
               10  FILLER           PIC X(7) VALUE 'DB020ZZ'.
               10  FILLER           PIC X(7) VALUE 'DB021ZZ'.
               10  FILLER           PIC X(7) VALUE 'DB022ZZ'.
               10  FILLER           PIC X(7) VALUE 'DB023ZZ'.
               10  FILLER           PIC X(7) VALUE 'DB023Z0'.
               10  FILLER           PIC X(7) VALUE 'DB024ZZ'.
               10  FILLER           PIC X(7) VALUE 'DB025ZZ'.
               10  FILLER           PIC X(7) VALUE 'DB026ZZ'.
               10  FILLER           PIC X(7) VALUE 'DB050ZZ'.
               10  FILLER           PIC X(7) VALUE 'DB051ZZ'.
               10  FILLER           PIC X(7) VALUE 'DB052ZZ'.
               10  FILLER           PIC X(7) VALUE 'DB053ZZ'.
               10  FILLER           PIC X(7) VALUE 'DB053Z0'.
               10  FILLER           PIC X(7) VALUE 'DB054ZZ'.
               10  FILLER           PIC X(7) VALUE 'DB055ZZ'.
               10  FILLER           PIC X(7) VALUE 'DB056ZZ'.
               10  FILLER           PIC X(7) VALUE 'DB060ZZ'.
               10  FILLER           PIC X(7) VALUE 'DB061ZZ'.
               10  FILLER           PIC X(7) VALUE 'DB062ZZ'.
               10  FILLER           PIC X(7) VALUE 'DB063ZZ'.
               10  FILLER           PIC X(7) VALUE 'DB063Z0'.
               10  FILLER           PIC X(7) VALUE 'DB064ZZ'.
               10  FILLER           PIC X(7) VALUE 'DB065ZZ'.
               10  FILLER           PIC X(7) VALUE 'DB066ZZ'.
               10  FILLER           PIC X(7) VALUE 'DB070ZZ'.
               10  FILLER           PIC X(7) VALUE 'DB071ZZ'.
               10  FILLER           PIC X(7) VALUE 'DB072ZZ'.
               10  FILLER           PIC X(7) VALUE 'DB073ZZ'.
               10  FILLER           PIC X(7) VALUE 'DB073Z0'.
               10  FILLER           PIC X(7) VALUE 'DB074ZZ'.
               10  FILLER           PIC X(7) VALUE 'DB075ZZ'.
               10  FILLER           PIC X(7) VALUE 'DB076ZZ'.
               10  FILLER           PIC X(7) VALUE 'DB080ZZ'.
               10  FILLER           PIC X(7) VALUE 'DB081ZZ'.
               10  FILLER           PIC X(7) VALUE 'DB082ZZ'.
               10  FILLER           PIC X(7) VALUE 'DB083ZZ'.
               10  FILLER           PIC X(7) VALUE 'DB083Z0'.
               10  FILLER           PIC X(7) VALUE 'DB084ZZ'.
               10  FILLER           PIC X(7) VALUE 'DB085ZZ'.
               10  FILLER           PIC X(7) VALUE 'DB086ZZ'.
               10  FILLER           PIC X(7) VALUE 'DB10BBZ'.
               10  FILLER           PIC X(7) VALUE 'DB10BB1'.
               10  FILLER           PIC X(7) VALUE 'DB10BCZ'.
               10  FILLER           PIC X(7) VALUE 'DB10BYZ'.
               10  FILLER           PIC X(7) VALUE 'DB10B6Z'.
               10  FILLER           PIC X(7) VALUE 'DB10B7Z'.
               10  FILLER           PIC X(7) VALUE 'DB10B8Z'.
               10  FILLER           PIC X(7) VALUE 'DB10B9Z'.
               10  FILLER           PIC X(7) VALUE 'DB109BZ'.
               10  FILLER           PIC X(7) VALUE 'DB109CZ'.
               10  FILLER           PIC X(7) VALUE 'DB109YZ'.
               10  FILLER           PIC X(7) VALUE 'DB1097Z'.
               10  FILLER           PIC X(7) VALUE 'DB1098Z'.
               10  FILLER           PIC X(7) VALUE 'DB1099Z'.
               10  FILLER           PIC X(7) VALUE 'DB11BBZ'.
               10  FILLER           PIC X(7) VALUE 'DB11BB1'.
               10  FILLER           PIC X(7) VALUE 'DB11BCZ'.
               10  FILLER           PIC X(7) VALUE 'DB11BYZ'.
               10  FILLER           PIC X(7) VALUE 'DB11B6Z'.
               10  FILLER           PIC X(7) VALUE 'DB11B7Z'.
               10  FILLER           PIC X(7) VALUE 'DB11B8Z'.
               10  FILLER           PIC X(7) VALUE 'DB11B9Z'.
               10  FILLER           PIC X(7) VALUE 'DB119BZ'.
               10  FILLER           PIC X(7) VALUE 'DB119CZ'.
               10  FILLER           PIC X(7) VALUE 'DB119YZ'.
               10  FILLER           PIC X(7) VALUE 'DB1197Z'.
               10  FILLER           PIC X(7) VALUE 'DB1198Z'.
               10  FILLER           PIC X(7) VALUE 'DB1199Z'.
               10  FILLER           PIC X(7) VALUE 'DB12BBZ'.
               10  FILLER           PIC X(7) VALUE 'DB12BB1'.
               10  FILLER           PIC X(7) VALUE 'DB12BCZ'.
               10  FILLER           PIC X(7) VALUE 'DB12BYZ'.
               10  FILLER           PIC X(7) VALUE 'DB12B6Z'.
               10  FILLER           PIC X(7) VALUE 'DB12B7Z'.
               10  FILLER           PIC X(7) VALUE 'DB12B8Z'.
               10  FILLER           PIC X(7) VALUE 'DB12B9Z'.
               10  FILLER           PIC X(7) VALUE 'DB129BZ'.
               10  FILLER           PIC X(7) VALUE 'DB129CZ'.
               10  FILLER           PIC X(7) VALUE 'DB129YZ'.
               10  FILLER           PIC X(7) VALUE 'DB1297Z'.
               10  FILLER           PIC X(7) VALUE 'DB1298Z'.
               10  FILLER           PIC X(7) VALUE 'DB1299Z'.
               10  FILLER           PIC X(7) VALUE 'DB15BBZ'.
               10  FILLER           PIC X(7) VALUE 'DB15BB1'.
               10  FILLER           PIC X(7) VALUE 'DB15BCZ'.
               10  FILLER           PIC X(7) VALUE 'DB15BYZ'.
               10  FILLER           PIC X(7) VALUE 'DB15B6Z'.
               10  FILLER           PIC X(7) VALUE 'DB15B7Z'.
               10  FILLER           PIC X(7) VALUE 'DB15B8Z'.
               10  FILLER           PIC X(7) VALUE 'DB15B9Z'.
               10  FILLER           PIC X(7) VALUE 'DB159BZ'.
               10  FILLER           PIC X(7) VALUE 'DB159CZ'.
               10  FILLER           PIC X(7) VALUE 'DB159YZ'.
               10  FILLER           PIC X(7) VALUE 'DB1597Z'.
               10  FILLER           PIC X(7) VALUE 'DB1598Z'.
               10  FILLER           PIC X(7) VALUE 'DB1599Z'.
               10  FILLER           PIC X(7) VALUE 'DB16BBZ'.
               10  FILLER           PIC X(7) VALUE 'DB16BB1'.
               10  FILLER           PIC X(7) VALUE 'DB16BCZ'.
               10  FILLER           PIC X(7) VALUE 'DB16BYZ'.
               10  FILLER           PIC X(7) VALUE 'DB16B6Z'.
               10  FILLER           PIC X(7) VALUE 'DB16B7Z'.
               10  FILLER           PIC X(7) VALUE 'DB16B8Z'.
               10  FILLER           PIC X(7) VALUE 'DB16B9Z'.
               10  FILLER           PIC X(7) VALUE 'DB169BZ'.
               10  FILLER           PIC X(7) VALUE 'DB169CZ'.
               10  FILLER           PIC X(7) VALUE 'DB169YZ'.
               10  FILLER           PIC X(7) VALUE 'DB1697Z'.
               10  FILLER           PIC X(7) VALUE 'DB1698Z'.
               10  FILLER           PIC X(7) VALUE 'DB1699Z'.
               10  FILLER           PIC X(7) VALUE 'DB17BBZ'.
               10  FILLER           PIC X(7) VALUE 'DB17BB1'.
               10  FILLER           PIC X(7) VALUE 'DB17BCZ'.
               10  FILLER           PIC X(7) VALUE 'DB17BYZ'.
               10  FILLER           PIC X(7) VALUE 'DB17B6Z'.
               10  FILLER           PIC X(7) VALUE 'DB17B7Z'.
               10  FILLER           PIC X(7) VALUE 'DB17B8Z'.
               10  FILLER           PIC X(7) VALUE 'DB17B9Z'.
               10  FILLER           PIC X(7) VALUE 'DB179BZ'.
               10  FILLER           PIC X(7) VALUE 'DB179CZ'.
               10  FILLER           PIC X(7) VALUE 'DB179YZ'.
               10  FILLER           PIC X(7) VALUE 'DB1797Z'.
               10  FILLER           PIC X(7) VALUE 'DB1798Z'.
               10  FILLER           PIC X(7) VALUE 'DB1799Z'.
               10  FILLER           PIC X(7) VALUE 'DB18BBZ'.
               10  FILLER           PIC X(7) VALUE 'DB18BB1'.
               10  FILLER           PIC X(7) VALUE 'DB18BCZ'.
               10  FILLER           PIC X(7) VALUE 'DB18BYZ'.
               10  FILLER           PIC X(7) VALUE 'DB18B6Z'.
               10  FILLER           PIC X(7) VALUE 'DB18B7Z'.
               10  FILLER           PIC X(7) VALUE 'DB18B8Z'.
               10  FILLER           PIC X(7) VALUE 'DB18B9Z'.
               10  FILLER           PIC X(7) VALUE 'DB189BZ'.
               10  FILLER           PIC X(7) VALUE 'DB189CZ'.
               10  FILLER           PIC X(7) VALUE 'DB189YZ'.
               10  FILLER           PIC X(7) VALUE 'DB1897Z'.
               10  FILLER           PIC X(7) VALUE 'DB1898Z'.
               10  FILLER           PIC X(7) VALUE 'DB1899Z'.
               10  FILLER           PIC X(7) VALUE 'DDY0FZZ'.
               10  FILLER           PIC X(7) VALUE 'DDY0KZZ'.
               10  FILLER           PIC X(7) VALUE 'DDY07ZZ'.
               10  FILLER           PIC X(7) VALUE 'DDY08ZZ'.
               10  FILLER           PIC X(7) VALUE 'DDY1CZZ'.
               10  FILLER           PIC X(7) VALUE 'DDY1FZZ'.
               10  FILLER           PIC X(7) VALUE 'DDY1KZZ'.
               10  FILLER           PIC X(7) VALUE 'DDY17ZZ'.
               10  FILLER           PIC X(7) VALUE 'DDY18ZZ'.
               10  FILLER           PIC X(7) VALUE 'DDY2CZZ'.
               10  FILLER           PIC X(7) VALUE 'DDY2FZZ'.
               10  FILLER           PIC X(7) VALUE 'DDY2KZZ'.
               10  FILLER           PIC X(7) VALUE 'DDY27ZZ'.
               10  FILLER           PIC X(7) VALUE 'DDY28ZZ'.
               10  FILLER           PIC X(7) VALUE 'DDY3CZZ'.
               10  FILLER           PIC X(7) VALUE 'DDY3FZZ'.
               10  FILLER           PIC X(7) VALUE 'DDY3KZZ'.
               10  FILLER           PIC X(7) VALUE 'DDY37ZZ'.
               10  FILLER           PIC X(7) VALUE 'DDY38ZZ'.
               10  FILLER           PIC X(7) VALUE 'DDY4CZZ'.
               10  FILLER           PIC X(7) VALUE 'DDY4FZZ'.
               10  FILLER           PIC X(7) VALUE 'DDY4KZZ'.
               10  FILLER           PIC X(7) VALUE 'DDY47ZZ'.
               10  FILLER           PIC X(7) VALUE 'DDY48ZZ'.
               10  FILLER           PIC X(7) VALUE 'DDY5CZZ'.
               10  FILLER           PIC X(7) VALUE 'DDY5FZZ'.
               10  FILLER           PIC X(7) VALUE 'DDY5KZZ'.
               10  FILLER           PIC X(7) VALUE 'DDY57ZZ'.
               10  FILLER           PIC X(7) VALUE 'DDY58ZZ'.
               10  FILLER           PIC X(7) VALUE 'DDY7CZZ'.
               10  FILLER           PIC X(7) VALUE 'DDY7FZZ'.
               10  FILLER           PIC X(7) VALUE 'DDY7KZZ'.
               10  FILLER           PIC X(7) VALUE 'DDY77ZZ'.
               10  FILLER           PIC X(7) VALUE 'DDY78ZZ'.
               10  FILLER           PIC X(7) VALUE 'DDY8CZZ'.
               10  FILLER           PIC X(7) VALUE 'DDY8FZZ'.
               10  FILLER           PIC X(7) VALUE 'DDY8KZZ'.
               10  FILLER           PIC X(7) VALUE 'DD000ZZ'.
               10  FILLER           PIC X(7) VALUE 'DD001ZZ'.
               10  FILLER           PIC X(7) VALUE 'DD002ZZ'.
               10  FILLER           PIC X(7) VALUE 'DD003ZZ'.
               10  FILLER           PIC X(7) VALUE 'DD003Z0'.
               10  FILLER           PIC X(7) VALUE 'DD004ZZ'.
               10  FILLER           PIC X(7) VALUE 'DD005ZZ'.
               10  FILLER           PIC X(7) VALUE 'DD006ZZ'.
               10  FILLER           PIC X(7) VALUE 'DD010ZZ'.
               10  FILLER           PIC X(7) VALUE 'DD011ZZ'.
               10  FILLER           PIC X(7) VALUE 'DD012ZZ'.
               10  FILLER           PIC X(7) VALUE 'DD013ZZ'.
               10  FILLER           PIC X(7) VALUE 'DD013Z0'.
               10  FILLER           PIC X(7) VALUE 'DD014ZZ'.
               10  FILLER           PIC X(7) VALUE 'DD015ZZ'.
               10  FILLER           PIC X(7) VALUE 'DD016ZZ'.
               10  FILLER           PIC X(7) VALUE 'DD020ZZ'.
               10  FILLER           PIC X(7) VALUE 'DD021ZZ'.
               10  FILLER           PIC X(7) VALUE 'DD022ZZ'.
               10  FILLER           PIC X(7) VALUE 'DD023ZZ'.
               10  FILLER           PIC X(7) VALUE 'DD023Z0'.
               10  FILLER           PIC X(7) VALUE 'DD024ZZ'.
               10  FILLER           PIC X(7) VALUE 'DD025ZZ'.
               10  FILLER           PIC X(7) VALUE 'DD026ZZ'.
               10  FILLER           PIC X(7) VALUE 'DD030ZZ'.
               10  FILLER           PIC X(7) VALUE 'DD031ZZ'.
               10  FILLER           PIC X(7) VALUE 'DD032ZZ'.
               10  FILLER           PIC X(7) VALUE 'DD033ZZ'.
               10  FILLER           PIC X(7) VALUE 'DD033Z0'.
               10  FILLER           PIC X(7) VALUE 'DD034ZZ'.
               10  FILLER           PIC X(7) VALUE 'DD035ZZ'.
               10  FILLER           PIC X(7) VALUE 'DD036ZZ'.
               10  FILLER           PIC X(7) VALUE 'DD040ZZ'.
               10  FILLER           PIC X(7) VALUE 'DD041ZZ'.
               10  FILLER           PIC X(7) VALUE 'DD042ZZ'.
               10  FILLER           PIC X(7) VALUE 'DD043ZZ'.
               10  FILLER           PIC X(7) VALUE 'DD043Z0'.
               10  FILLER           PIC X(7) VALUE 'DD044ZZ'.
               10  FILLER           PIC X(7) VALUE 'DD045ZZ'.
               10  FILLER           PIC X(7) VALUE 'DD046ZZ'.
               10  FILLER           PIC X(7) VALUE 'DD050ZZ'.
               10  FILLER           PIC X(7) VALUE 'DD051ZZ'.
               10  FILLER           PIC X(7) VALUE 'DD052ZZ'.
               10  FILLER           PIC X(7) VALUE 'DD053ZZ'.
               10  FILLER           PIC X(7) VALUE 'DD053Z0'.
               10  FILLER           PIC X(7) VALUE 'DD054ZZ'.
               10  FILLER           PIC X(7) VALUE 'DD055ZZ'.
               10  FILLER           PIC X(7) VALUE 'DD056ZZ'.
               10  FILLER           PIC X(7) VALUE 'DD070ZZ'.
               10  FILLER           PIC X(7) VALUE 'DD071ZZ'.
               10  FILLER           PIC X(7) VALUE 'DD072ZZ'.
               10  FILLER           PIC X(7) VALUE 'DD073ZZ'.
               10  FILLER           PIC X(7) VALUE 'DD073Z0'.
               10  FILLER           PIC X(7) VALUE 'DD074ZZ'.
               10  FILLER           PIC X(7) VALUE 'DD075ZZ'.
               10  FILLER           PIC X(7) VALUE 'DD076ZZ'.
               10  FILLER           PIC X(7) VALUE 'DD10BBZ'.
               10  FILLER           PIC X(7) VALUE 'DD10BB1'.
               10  FILLER           PIC X(7) VALUE 'DD10BCZ'.
               10  FILLER           PIC X(7) VALUE 'DD10BYZ'.
               10  FILLER           PIC X(7) VALUE 'DD10B6Z'.
               10  FILLER           PIC X(7) VALUE 'DD10B7Z'.
               10  FILLER           PIC X(7) VALUE 'DD10B8Z'.
               10  FILLER           PIC X(7) VALUE 'DD10B9Z'.
               10  FILLER           PIC X(7) VALUE 'DD109BZ'.
               10  FILLER           PIC X(7) VALUE 'DD109CZ'.
               10  FILLER           PIC X(7) VALUE 'DD109YZ'.
               10  FILLER           PIC X(7) VALUE 'DD1097Z'.
               10  FILLER           PIC X(7) VALUE 'DD1098Z'.
               10  FILLER           PIC X(7) VALUE 'DD1099Z'.
               10  FILLER           PIC X(7) VALUE 'DD11BBZ'.
               10  FILLER           PIC X(7) VALUE 'DD11BB1'.
               10  FILLER           PIC X(7) VALUE 'DD11BCZ'.
               10  FILLER           PIC X(7) VALUE 'DD11BYZ'.
               10  FILLER           PIC X(7) VALUE 'DD11B6Z'.
               10  FILLER           PIC X(7) VALUE 'DD11B7Z'.
               10  FILLER           PIC X(7) VALUE 'DD11B8Z'.
               10  FILLER           PIC X(7) VALUE 'DD11B9Z'.
               10  FILLER           PIC X(7) VALUE 'DD119BZ'.
               10  FILLER           PIC X(7) VALUE 'DD119CZ'.
               10  FILLER           PIC X(7) VALUE 'DD119YZ'.
               10  FILLER           PIC X(7) VALUE 'DD1197Z'.
               10  FILLER           PIC X(7) VALUE 'DD1198Z'.
               10  FILLER           PIC X(7) VALUE 'DD1199Z'.
               10  FILLER           PIC X(7) VALUE 'DD12BBZ'.
               10  FILLER           PIC X(7) VALUE 'DD12BB1'.
               10  FILLER           PIC X(7) VALUE 'DD12BCZ'.
               10  FILLER           PIC X(7) VALUE 'DD12BYZ'.
               10  FILLER           PIC X(7) VALUE 'DD12B6Z'.
               10  FILLER           PIC X(7) VALUE 'DD12B7Z'.
               10  FILLER           PIC X(7) VALUE 'DD12B8Z'.
               10  FILLER           PIC X(7) VALUE 'DD12B9Z'.
               10  FILLER           PIC X(7) VALUE 'DD129BZ'.
               10  FILLER           PIC X(7) VALUE 'DD129CZ'.
               10  FILLER           PIC X(7) VALUE 'DD129YZ'.
               10  FILLER           PIC X(7) VALUE 'DD1297Z'.
               10  FILLER           PIC X(7) VALUE 'DD1298Z'.
               10  FILLER           PIC X(7) VALUE 'DD1299Z'.
               10  FILLER           PIC X(7) VALUE 'DD13BBZ'.
               10  FILLER           PIC X(7) VALUE 'DD13BB1'.
               10  FILLER           PIC X(7) VALUE 'DD13BCZ'.
               10  FILLER           PIC X(7) VALUE 'DD13BYZ'.
               10  FILLER           PIC X(7) VALUE 'DD13B6Z'.
               10  FILLER           PIC X(7) VALUE 'DD13B7Z'.
               10  FILLER           PIC X(7) VALUE 'DD13B8Z'.
               10  FILLER           PIC X(7) VALUE 'DD13B9Z'.
               10  FILLER           PIC X(7) VALUE 'DD139BZ'.
               10  FILLER           PIC X(7) VALUE 'DD139CZ'.
               10  FILLER           PIC X(7) VALUE 'DD139YZ'.
               10  FILLER           PIC X(7) VALUE 'DD1397Z'.
               10  FILLER           PIC X(7) VALUE 'DD1398Z'.
               10  FILLER           PIC X(7) VALUE 'DD1399Z'.
               10  FILLER           PIC X(7) VALUE 'DD14BBZ'.
               10  FILLER           PIC X(7) VALUE 'DD14BB1'.
               10  FILLER           PIC X(7) VALUE 'DD14BCZ'.
               10  FILLER           PIC X(7) VALUE 'DD14BYZ'.
               10  FILLER           PIC X(7) VALUE 'DD14B6Z'.
               10  FILLER           PIC X(7) VALUE 'DD14B7Z'.
               10  FILLER           PIC X(7) VALUE 'DD14B8Z'.
               10  FILLER           PIC X(7) VALUE 'DD14B9Z'.
               10  FILLER           PIC X(7) VALUE 'DD149BZ'.
               10  FILLER           PIC X(7) VALUE 'DD149CZ'.
               10  FILLER           PIC X(7) VALUE 'DD149YZ'.
               10  FILLER           PIC X(7) VALUE 'DD1497Z'.
               10  FILLER           PIC X(7) VALUE 'DD1498Z'.
               10  FILLER           PIC X(7) VALUE 'DD1499Z'.
               10  FILLER           PIC X(7) VALUE 'DD15BBZ'.
               10  FILLER           PIC X(7) VALUE 'DD15BB1'.
               10  FILLER           PIC X(7) VALUE 'DD15BCZ'.
               10  FILLER           PIC X(7) VALUE 'DD15BYZ'.
               10  FILLER           PIC X(7) VALUE 'DD15B6Z'.
               10  FILLER           PIC X(7) VALUE 'DD15B7Z'.
               10  FILLER           PIC X(7) VALUE 'DD15B8Z'.
               10  FILLER           PIC X(7) VALUE 'DD15B9Z'.
               10  FILLER           PIC X(7) VALUE 'DD159BZ'.
               10  FILLER           PIC X(7) VALUE 'DD159CZ'.
               10  FILLER           PIC X(7) VALUE 'DD159YZ'.
               10  FILLER           PIC X(7) VALUE 'DD1597Z'.
               10  FILLER           PIC X(7) VALUE 'DD1598Z'.
               10  FILLER           PIC X(7) VALUE 'DD1599Z'.
               10  FILLER           PIC X(7) VALUE 'DD17BBZ'.
               10  FILLER           PIC X(7) VALUE 'DD17BB1'.
               10  FILLER           PIC X(7) VALUE 'DD17BCZ'.
               10  FILLER           PIC X(7) VALUE 'DD17BYZ'.
               10  FILLER           PIC X(7) VALUE 'DD17B6Z'.
               10  FILLER           PIC X(7) VALUE 'DD17B7Z'.
               10  FILLER           PIC X(7) VALUE 'DD17B8Z'.
               10  FILLER           PIC X(7) VALUE 'DD17B9Z'.
               10  FILLER           PIC X(7) VALUE 'DD179BZ'.
               10  FILLER           PIC X(7) VALUE 'DD179CZ'.
               10  FILLER           PIC X(7) VALUE 'DD179YZ'.
               10  FILLER           PIC X(7) VALUE 'DD1797Z'.
               10  FILLER           PIC X(7) VALUE 'DD1798Z'.
               10  FILLER           PIC X(7) VALUE 'DD1799Z'.
               10  FILLER           PIC X(7) VALUE 'DFY0CZZ'.
               10  FILLER           PIC X(7) VALUE 'DFY0FZZ'.
               10  FILLER           PIC X(7) VALUE 'DFY0KZZ'.
               10  FILLER           PIC X(7) VALUE 'DFY07ZZ'.
               10  FILLER           PIC X(7) VALUE 'DFY08ZZ'.
               10  FILLER           PIC X(7) VALUE 'DFY1CZZ'.
               10  FILLER           PIC X(7) VALUE 'DFY1FZZ'.
               10  FILLER           PIC X(7) VALUE 'DFY1KZZ'.
               10  FILLER           PIC X(7) VALUE 'DFY17ZZ'.
               10  FILLER           PIC X(7) VALUE 'DFY18ZZ'.
               10  FILLER           PIC X(7) VALUE 'DFY2CZZ'.
               10  FILLER           PIC X(7) VALUE 'DFY2FZZ'.
               10  FILLER           PIC X(7) VALUE 'DFY2KZZ'.
               10  FILLER           PIC X(7) VALUE 'DFY27ZZ'.
               10  FILLER           PIC X(7) VALUE 'DFY28ZZ'.
               10  FILLER           PIC X(7) VALUE 'DFY3CZZ'.
               10  FILLER           PIC X(7) VALUE 'DFY3FZZ'.
               10  FILLER           PIC X(7) VALUE 'DFY3KZZ'.
               10  FILLER           PIC X(7) VALUE 'DFY37ZZ'.
               10  FILLER           PIC X(7) VALUE 'DFY38ZZ'.
               10  FILLER           PIC X(7) VALUE 'DF000ZZ'.
               10  FILLER           PIC X(7) VALUE 'DF001ZZ'.
               10  FILLER           PIC X(7) VALUE 'DF002ZZ'.
               10  FILLER           PIC X(7) VALUE 'DF003ZZ'.
               10  FILLER           PIC X(7) VALUE 'DF003Z0'.
               10  FILLER           PIC X(7) VALUE 'DF004ZZ'.
               10  FILLER           PIC X(7) VALUE 'DF005ZZ'.
               10  FILLER           PIC X(7) VALUE 'DF006ZZ'.
               10  FILLER           PIC X(7) VALUE 'DF010ZZ'.
               10  FILLER           PIC X(7) VALUE 'DF011ZZ'.
               10  FILLER           PIC X(7) VALUE 'DF012ZZ'.
               10  FILLER           PIC X(7) VALUE 'DF013ZZ'.
               10  FILLER           PIC X(7) VALUE 'DF013Z0'.
               10  FILLER           PIC X(7) VALUE 'DF014ZZ'.
               10  FILLER           PIC X(7) VALUE 'DF015ZZ'.
               10  FILLER           PIC X(7) VALUE 'DF016ZZ'.
               10  FILLER           PIC X(7) VALUE 'DF020ZZ'.
               10  FILLER           PIC X(7) VALUE 'DF021ZZ'.
               10  FILLER           PIC X(7) VALUE 'DF022ZZ'.
               10  FILLER           PIC X(7) VALUE 'DF023ZZ'.
               10  FILLER           PIC X(7) VALUE 'DF023Z0'.
               10  FILLER           PIC X(7) VALUE 'DF024ZZ'.
               10  FILLER           PIC X(7) VALUE 'DF025ZZ'.
               10  FILLER           PIC X(7) VALUE 'DF026ZZ'.
               10  FILLER           PIC X(7) VALUE 'DF030ZZ'.
               10  FILLER           PIC X(7) VALUE 'DF031ZZ'.
               10  FILLER           PIC X(7) VALUE 'DF032ZZ'.
               10  FILLER           PIC X(7) VALUE 'DF033ZZ'.
               10  FILLER           PIC X(7) VALUE 'DF033Z0'.
               10  FILLER           PIC X(7) VALUE 'DF034ZZ'.
               10  FILLER           PIC X(7) VALUE 'DF035ZZ'.
               10  FILLER           PIC X(7) VALUE 'DF036ZZ'.
               10  FILLER           PIC X(7) VALUE 'DF10BBZ'.
               10  FILLER           PIC X(7) VALUE 'DF10BB1'.
               10  FILLER           PIC X(7) VALUE 'DF10BCZ'.
               10  FILLER           PIC X(7) VALUE 'DF10BYZ'.
               10  FILLER           PIC X(7) VALUE 'DF10B6Z'.
               10  FILLER           PIC X(7) VALUE 'DF10B7Z'.
               10  FILLER           PIC X(7) VALUE 'DF10B8Z'.
               10  FILLER           PIC X(7) VALUE 'DF10B9Z'.
               10  FILLER           PIC X(7) VALUE 'DF109BZ'.
               10  FILLER           PIC X(7) VALUE 'DF109CZ'.
               10  FILLER           PIC X(7) VALUE 'DF109YZ'.
               10  FILLER           PIC X(7) VALUE 'DF1097Z'.
               10  FILLER           PIC X(7) VALUE 'DF1098Z'.
               10  FILLER           PIC X(7) VALUE 'DF1099Z'.
               10  FILLER           PIC X(7) VALUE 'DF11BBZ'.
               10  FILLER           PIC X(7) VALUE 'DF11BB1'.
               10  FILLER           PIC X(7) VALUE 'DF11BCZ'.
               10  FILLER           PIC X(7) VALUE 'DF11BYZ'.
               10  FILLER           PIC X(7) VALUE 'DF11B6Z'.
               10  FILLER           PIC X(7) VALUE 'DF11B7Z'.
               10  FILLER           PIC X(7) VALUE 'DF11B8Z'.
               10  FILLER           PIC X(7) VALUE 'DF11B9Z'.
               10  FILLER           PIC X(7) VALUE 'DF119BZ'.
               10  FILLER           PIC X(7) VALUE 'DF119CZ'.
               10  FILLER           PIC X(7) VALUE 'DF119YZ'.
               10  FILLER           PIC X(7) VALUE 'DF1197Z'.
               10  FILLER           PIC X(7) VALUE 'DF1198Z'.
               10  FILLER           PIC X(7) VALUE 'DF1199Z'.
               10  FILLER           PIC X(7) VALUE 'DF12BBZ'.
               10  FILLER           PIC X(7) VALUE 'DF12BB1'.
               10  FILLER           PIC X(7) VALUE 'DF12BCZ'.
               10  FILLER           PIC X(7) VALUE 'DF12BYZ'.
               10  FILLER           PIC X(7) VALUE 'DF12B6Z'.
               10  FILLER           PIC X(7) VALUE 'DF12B7Z'.
               10  FILLER           PIC X(7) VALUE 'DF12B8Z'.
               10  FILLER           PIC X(7) VALUE 'DF12B9Z'.
               10  FILLER           PIC X(7) VALUE 'DF129BZ'.
               10  FILLER           PIC X(7) VALUE 'DF129CZ'.
               10  FILLER           PIC X(7) VALUE 'DF129YZ'.
               10  FILLER           PIC X(7) VALUE 'DF1297Z'.
               10  FILLER           PIC X(7) VALUE 'DF1298Z'.
               10  FILLER           PIC X(7) VALUE 'DF1299Z'.
               10  FILLER           PIC X(7) VALUE 'DF13BBZ'.
               10  FILLER           PIC X(7) VALUE 'DF13BB1'.
               10  FILLER           PIC X(7) VALUE 'DF13BCZ'.
               10  FILLER           PIC X(7) VALUE 'DF13BYZ'.
               10  FILLER           PIC X(7) VALUE 'DF13B6Z'.
               10  FILLER           PIC X(7) VALUE 'DF13B7Z'.
               10  FILLER           PIC X(7) VALUE 'DF13B8Z'.
               10  FILLER           PIC X(7) VALUE 'DF13B9Z'.
               10  FILLER           PIC X(7) VALUE 'DF139BZ'.
               10  FILLER           PIC X(7) VALUE 'DF139CZ'.
               10  FILLER           PIC X(7) VALUE 'DF139YZ'.
               10  FILLER           PIC X(7) VALUE 'DF1397Z'.
               10  FILLER           PIC X(7) VALUE 'DF1398Z'.
               10  FILLER           PIC X(7) VALUE 'DF1399Z'.
               10  FILLER           PIC X(7) VALUE 'DGY0FZZ'.
               10  FILLER           PIC X(7) VALUE 'DGY0KZZ'.
               10  FILLER           PIC X(7) VALUE 'DGY07ZZ'.
               10  FILLER           PIC X(7) VALUE 'DGY08ZZ'.
               10  FILLER           PIC X(7) VALUE 'DGY1FZZ'.
               10  FILLER           PIC X(7) VALUE 'DGY1KZZ'.
               10  FILLER           PIC X(7) VALUE 'DGY17ZZ'.
               10  FILLER           PIC X(7) VALUE 'DGY18ZZ'.
               10  FILLER           PIC X(7) VALUE 'DGY2FZZ'.
               10  FILLER           PIC X(7) VALUE 'DGY2KZZ'.
               10  FILLER           PIC X(7) VALUE 'DGY27ZZ'.
               10  FILLER           PIC X(7) VALUE 'DGY28ZZ'.
               10  FILLER           PIC X(7) VALUE 'DGY4FZZ'.
               10  FILLER           PIC X(7) VALUE 'DGY4KZZ'.
               10  FILLER           PIC X(7) VALUE 'DGY47ZZ'.
               10  FILLER           PIC X(7) VALUE 'DGY48ZZ'.
               10  FILLER           PIC X(7) VALUE 'DGY5FZZ'.
               10  FILLER           PIC X(7) VALUE 'DGY5KZZ'.
               10  FILLER           PIC X(7) VALUE 'DGY57ZZ'.
               10  FILLER           PIC X(7) VALUE 'DGY58ZZ'.
               10  FILLER           PIC X(7) VALUE 'DG000ZZ'.
               10  FILLER           PIC X(7) VALUE 'DG001ZZ'.
               10  FILLER           PIC X(7) VALUE 'DG002ZZ'.
               10  FILLER           PIC X(7) VALUE 'DG003ZZ'.
               10  FILLER           PIC X(7) VALUE 'DG003Z0'.
               10  FILLER           PIC X(7) VALUE 'DG005ZZ'.
               10  FILLER           PIC X(7) VALUE 'DG006ZZ'.
               10  FILLER           PIC X(7) VALUE 'DG010ZZ'.
               10  FILLER           PIC X(7) VALUE 'DG011ZZ'.
               10  FILLER           PIC X(7) VALUE 'DG012ZZ'.
               10  FILLER           PIC X(7) VALUE 'DG013ZZ'.
               10  FILLER           PIC X(7) VALUE 'DG013Z0'.
               10  FILLER           PIC X(7) VALUE 'DG015ZZ'.
               10  FILLER           PIC X(7) VALUE 'DG016ZZ'.
               10  FILLER           PIC X(7) VALUE 'DG020ZZ'.
               10  FILLER           PIC X(7) VALUE 'DG021ZZ'.
               10  FILLER           PIC X(7) VALUE 'DG022ZZ'.
               10  FILLER           PIC X(7) VALUE 'DG023ZZ'.
               10  FILLER           PIC X(7) VALUE 'DG023Z0'.
               10  FILLER           PIC X(7) VALUE 'DG025ZZ'.
               10  FILLER           PIC X(7) VALUE 'DG026ZZ'.
               10  FILLER           PIC X(7) VALUE 'DG040ZZ'.
               10  FILLER           PIC X(7) VALUE 'DG041ZZ'.
               10  FILLER           PIC X(7) VALUE 'DG042ZZ'.
               10  FILLER           PIC X(7) VALUE 'DG043ZZ'.
               10  FILLER           PIC X(7) VALUE 'DG043Z0'.
               10  FILLER           PIC X(7) VALUE 'DG045ZZ'.
               10  FILLER           PIC X(7) VALUE 'DG046ZZ'.
               10  FILLER           PIC X(7) VALUE 'DG050ZZ'.
               10  FILLER           PIC X(7) VALUE 'DG051ZZ'.
               10  FILLER           PIC X(7) VALUE 'DG052ZZ'.
               10  FILLER           PIC X(7) VALUE 'DG053ZZ'.
               10  FILLER           PIC X(7) VALUE 'DG053Z0'.
               10  FILLER           PIC X(7) VALUE 'DG055ZZ'.
               10  FILLER           PIC X(7) VALUE 'DG056ZZ'.
               10  FILLER           PIC X(7) VALUE 'DG10BBZ'.
               10  FILLER           PIC X(7) VALUE 'DG10BB1'.
               10  FILLER           PIC X(7) VALUE 'DG10BCZ'.
               10  FILLER           PIC X(7) VALUE 'DG10BYZ'.
               10  FILLER           PIC X(7) VALUE 'DG10B6Z'.
               10  FILLER           PIC X(7) VALUE 'DG10B7Z'.
               10  FILLER           PIC X(7) VALUE 'DG10B8Z'.
               10  FILLER           PIC X(7) VALUE 'DG10B9Z'.
               10  FILLER           PIC X(7) VALUE 'DG109BZ'.
               10  FILLER           PIC X(7) VALUE 'DG109CZ'.
               10  FILLER           PIC X(7) VALUE 'DG109YZ'.
               10  FILLER           PIC X(7) VALUE 'DG1097Z'.
               10  FILLER           PIC X(7) VALUE 'DG1098Z'.
               10  FILLER           PIC X(7) VALUE 'DG1099Z'.
               10  FILLER           PIC X(7) VALUE 'DG11BBZ'.
               10  FILLER           PIC X(7) VALUE 'DG11BB1'.
               10  FILLER           PIC X(7) VALUE 'DG11BCZ'.
               10  FILLER           PIC X(7) VALUE 'DG11BYZ'.
               10  FILLER           PIC X(7) VALUE 'DG11B6Z'.
               10  FILLER           PIC X(7) VALUE 'DG11B7Z'.
               10  FILLER           PIC X(7) VALUE 'DG11B8Z'.
               10  FILLER           PIC X(7) VALUE 'DG11B9Z'.
               10  FILLER           PIC X(7) VALUE 'DG119BZ'.
               10  FILLER           PIC X(7) VALUE 'DG119CZ'.
               10  FILLER           PIC X(7) VALUE 'DG119YZ'.
               10  FILLER           PIC X(7) VALUE 'DG1197Z'.
               10  FILLER           PIC X(7) VALUE 'DG1198Z'.
               10  FILLER           PIC X(7) VALUE 'DG1199Z'.
               10  FILLER           PIC X(7) VALUE 'DG12BBZ'.
               10  FILLER           PIC X(7) VALUE 'DG12BB1'.
               10  FILLER           PIC X(7) VALUE 'DG12BCZ'.
               10  FILLER           PIC X(7) VALUE 'DG12BYZ'.
               10  FILLER           PIC X(7) VALUE 'DG12B6Z'.
               10  FILLER           PIC X(7) VALUE 'DG12B7Z'.
               10  FILLER           PIC X(7) VALUE 'DG12B8Z'.
               10  FILLER           PIC X(7) VALUE 'DG12B9Z'.
               10  FILLER           PIC X(7) VALUE 'DG129BZ'.
               10  FILLER           PIC X(7) VALUE 'DG129CZ'.
               10  FILLER           PIC X(7) VALUE 'DG129YZ'.
               10  FILLER           PIC X(7) VALUE 'DG1297Z'.
               10  FILLER           PIC X(7) VALUE 'DG1298Z'.
               10  FILLER           PIC X(7) VALUE 'DG1299Z'.
               10  FILLER           PIC X(7) VALUE 'DG14BBZ'.
               10  FILLER           PIC X(7) VALUE 'DG14BB1'.
               10  FILLER           PIC X(7) VALUE 'DG14BCZ'.
               10  FILLER           PIC X(7) VALUE 'DG14BYZ'.
               10  FILLER           PIC X(7) VALUE 'DG14B6Z'.
               10  FILLER           PIC X(7) VALUE 'DG14B7Z'.
               10  FILLER           PIC X(7) VALUE 'DG14B8Z'.
               10  FILLER           PIC X(7) VALUE 'DG14B9Z'.
               10  FILLER           PIC X(7) VALUE 'DG149BZ'.
               10  FILLER           PIC X(7) VALUE 'DG149CZ'.
               10  FILLER           PIC X(7) VALUE 'DG149YZ'.
               10  FILLER           PIC X(7) VALUE 'DG1497Z'.
               10  FILLER           PIC X(7) VALUE 'DG1498Z'.
               10  FILLER           PIC X(7) VALUE 'DG1499Z'.
               10  FILLER           PIC X(7) VALUE 'DG15BBZ'.
               10  FILLER           PIC X(7) VALUE 'DG15BB1'.
               10  FILLER           PIC X(7) VALUE 'DG15BCZ'.
               10  FILLER           PIC X(7) VALUE 'DG15BYZ'.
               10  FILLER           PIC X(7) VALUE 'DG15B6Z'.
               10  FILLER           PIC X(7) VALUE 'DG15B7Z'.
               10  FILLER           PIC X(7) VALUE 'DG15B8Z'.
               10  FILLER           PIC X(7) VALUE 'DG15B9Z'.
               10  FILLER           PIC X(7) VALUE 'DG159BZ'.
               10  FILLER           PIC X(7) VALUE 'DG159CZ'.
               10  FILLER           PIC X(7) VALUE 'DG159YZ'.
               10  FILLER           PIC X(7) VALUE 'DG1597Z'.
               10  FILLER           PIC X(7) VALUE 'DG1598Z'.
               10  FILLER           PIC X(7) VALUE 'DG1599Z'.
               10  FILLER           PIC X(7) VALUE 'DHYBFZZ'.
               10  FILLER           PIC X(7) VALUE 'DHYB7ZZ'.
               10  FILLER           PIC X(7) VALUE 'DHYB8ZZ'.
               10  FILLER           PIC X(7) VALUE 'DHYCFZZ'.
               10  FILLER           PIC X(7) VALUE 'DHY2FZZ'.
               10  FILLER           PIC X(7) VALUE 'DHY27ZZ'.
               10  FILLER           PIC X(7) VALUE 'DHY28ZZ'.
               10  FILLER           PIC X(7) VALUE 'DHY3FZZ'.
               10  FILLER           PIC X(7) VALUE 'DHY37ZZ'.
               10  FILLER           PIC X(7) VALUE 'DHY38ZZ'.
               10  FILLER           PIC X(7) VALUE 'DHY4FZZ'.
               10  FILLER           PIC X(7) VALUE 'DHY47ZZ'.
               10  FILLER           PIC X(7) VALUE 'DHY48ZZ'.
               10  FILLER           PIC X(7) VALUE 'DHY5FZZ'.
               10  FILLER           PIC X(7) VALUE 'DHY6FZZ'.
               10  FILLER           PIC X(7) VALUE 'DHY67ZZ'.
               10  FILLER           PIC X(7) VALUE 'DHY68ZZ'.
               10  FILLER           PIC X(7) VALUE 'DHY7FZZ'.
               10  FILLER           PIC X(7) VALUE 'DHY77ZZ'.
               10  FILLER           PIC X(7) VALUE 'DHY78ZZ'.
               10  FILLER           PIC X(7) VALUE 'DHY8FZZ'.
               10  FILLER           PIC X(7) VALUE 'DHY87ZZ'.
               10  FILLER           PIC X(7) VALUE 'DHY88ZZ'.
               10  FILLER           PIC X(7) VALUE 'DHY9FZZ'.
               10  FILLER           PIC X(7) VALUE 'DHY97ZZ'.
               10  FILLER           PIC X(7) VALUE 'DHY98ZZ'.
               10  FILLER           PIC X(7) VALUE 'DH0B0ZZ'.
               10  FILLER           PIC X(7) VALUE 'DH0B1ZZ'.
               10  FILLER           PIC X(7) VALUE 'DH0B2ZZ'.
               10  FILLER           PIC X(7) VALUE 'DH0B3ZZ'.
               10  FILLER           PIC X(7) VALUE 'DH0B3Z0'.
               10  FILLER           PIC X(7) VALUE 'DH0B4ZZ'.
               10  FILLER           PIC X(7) VALUE 'DH0B5ZZ'.
               10  FILLER           PIC X(7) VALUE 'DH0B6ZZ'.
               10  FILLER           PIC X(7) VALUE 'DH020ZZ'.
               10  FILLER           PIC X(7) VALUE 'DH021ZZ'.
               10  FILLER           PIC X(7) VALUE 'DH022ZZ'.
               10  FILLER           PIC X(7) VALUE 'DH023ZZ'.
               10  FILLER           PIC X(7) VALUE 'DH023Z0'.
               10  FILLER           PIC X(7) VALUE 'DH024ZZ'.
               10  FILLER           PIC X(7) VALUE 'DH025ZZ'.
               10  FILLER           PIC X(7) VALUE 'DH026ZZ'.
               10  FILLER           PIC X(7) VALUE 'DH030ZZ'.
               10  FILLER           PIC X(7) VALUE 'DH031ZZ'.
               10  FILLER           PIC X(7) VALUE 'DH032ZZ'.
               10  FILLER           PIC X(7) VALUE 'DH033ZZ'.
               10  FILLER           PIC X(7) VALUE 'DH033Z0'.
               10  FILLER           PIC X(7) VALUE 'DH034ZZ'.
               10  FILLER           PIC X(7) VALUE 'DH035ZZ'.
               10  FILLER           PIC X(7) VALUE 'DH036ZZ'.
               10  FILLER           PIC X(7) VALUE 'DH040ZZ'.
               10  FILLER           PIC X(7) VALUE 'DH041ZZ'.
               10  FILLER           PIC X(7) VALUE 'DH042ZZ'.
               10  FILLER           PIC X(7) VALUE 'DH043ZZ'.
               10  FILLER           PIC X(7) VALUE 'DH043Z0'.
               10  FILLER           PIC X(7) VALUE 'DH044ZZ'.
               10  FILLER           PIC X(7) VALUE 'DH045ZZ'.
               10  FILLER           PIC X(7) VALUE 'DH046ZZ'.
               10  FILLER           PIC X(7) VALUE 'DH060ZZ'.
               10  FILLER           PIC X(7) VALUE 'DH061ZZ'.
               10  FILLER           PIC X(7) VALUE 'DH062ZZ'.
               10  FILLER           PIC X(7) VALUE 'DH063ZZ'.
               10  FILLER           PIC X(7) VALUE 'DH063Z0'.
               10  FILLER           PIC X(7) VALUE 'DH064ZZ'.
               10  FILLER           PIC X(7) VALUE 'DH065ZZ'.
               10  FILLER           PIC X(7) VALUE 'DH066ZZ'.
               10  FILLER           PIC X(7) VALUE 'DH070ZZ'.
               10  FILLER           PIC X(7) VALUE 'DH071ZZ'.
               10  FILLER           PIC X(7) VALUE 'DH072ZZ'.
               10  FILLER           PIC X(7) VALUE 'DH073ZZ'.
               10  FILLER           PIC X(7) VALUE 'DH073Z0'.
               10  FILLER           PIC X(7) VALUE 'DH074ZZ'.
               10  FILLER           PIC X(7) VALUE 'DH075ZZ'.
               10  FILLER           PIC X(7) VALUE 'DH076ZZ'.
               10  FILLER           PIC X(7) VALUE 'DH080ZZ'.
               10  FILLER           PIC X(7) VALUE 'DH081ZZ'.
               10  FILLER           PIC X(7) VALUE 'DH082ZZ'.
               10  FILLER           PIC X(7) VALUE 'DH083ZZ'.
               10  FILLER           PIC X(7) VALUE 'DH083Z0'.
               10  FILLER           PIC X(7) VALUE 'DH084ZZ'.
               10  FILLER           PIC X(7) VALUE 'DH085ZZ'.
               10  FILLER           PIC X(7) VALUE 'DH086ZZ'.
               10  FILLER           PIC X(7) VALUE 'DH090ZZ'.
               10  FILLER           PIC X(7) VALUE 'DH091ZZ'.
               10  FILLER           PIC X(7) VALUE 'DH092ZZ'.
               10  FILLER           PIC X(7) VALUE 'DH093ZZ'.
               10  FILLER           PIC X(7) VALUE 'DH093Z0'.
               10  FILLER           PIC X(7) VALUE 'DH094ZZ'.
               10  FILLER           PIC X(7) VALUE 'DH095ZZ'.
               10  FILLER           PIC X(7) VALUE 'DH096ZZ'.
               10  FILLER           PIC X(7) VALUE 'DMY0FZZ'.
               10  FILLER           PIC X(7) VALUE 'DMY0KZZ'.
               10  FILLER           PIC X(7) VALUE 'DMY07ZZ'.
               10  FILLER           PIC X(7) VALUE 'DMY08ZZ'.
               10  FILLER           PIC X(7) VALUE 'DMY1FZZ'.
               10  FILLER           PIC X(7) VALUE 'DMY1KZZ'.
               10  FILLER           PIC X(7) VALUE 'DMY17ZZ'.
               10  FILLER           PIC X(7) VALUE 'DMY18ZZ'.
               10  FILLER           PIC X(7) VALUE 'DM000ZZ'.
               10  FILLER           PIC X(7) VALUE 'DM001ZZ'.
               10  FILLER           PIC X(7) VALUE 'DM002ZZ'.
               10  FILLER           PIC X(7) VALUE 'DM003ZZ'.
               10  FILLER           PIC X(7) VALUE 'DM003Z0'.
               10  FILLER           PIC X(7) VALUE 'DM004ZZ'.
               10  FILLER           PIC X(7) VALUE 'DM005ZZ'.
               10  FILLER           PIC X(7) VALUE 'DM006ZZ'.
               10  FILLER           PIC X(7) VALUE 'DM010ZZ'.
               10  FILLER           PIC X(7) VALUE 'DM011ZZ'.
               10  FILLER           PIC X(7) VALUE 'DM012ZZ'.
               10  FILLER           PIC X(7) VALUE 'DM013ZZ'.
               10  FILLER           PIC X(7) VALUE 'DM013Z0'.
               10  FILLER           PIC X(7) VALUE 'DM014ZZ'.
               10  FILLER           PIC X(7) VALUE 'DM015ZZ'.
               10  FILLER           PIC X(7) VALUE 'DM016ZZ'.
               10  FILLER           PIC X(7) VALUE 'DM10BBZ'.
               10  FILLER           PIC X(7) VALUE 'DM10BB1'.
               10  FILLER           PIC X(7) VALUE 'DM10BCZ'.
               10  FILLER           PIC X(7) VALUE 'DM10BYZ'.
               10  FILLER           PIC X(7) VALUE 'DM10B6Z'.
               10  FILLER           PIC X(7) VALUE 'DM10B7Z'.
               10  FILLER           PIC X(7) VALUE 'DM10B8Z'.
               10  FILLER           PIC X(7) VALUE 'DM10B9Z'.
               10  FILLER           PIC X(7) VALUE 'DM109BZ'.
               10  FILLER           PIC X(7) VALUE 'DM109CZ'.
               10  FILLER           PIC X(7) VALUE 'DM109YZ'.
               10  FILLER           PIC X(7) VALUE 'DM1097Z'.
               10  FILLER           PIC X(7) VALUE 'DM1098Z'.
               10  FILLER           PIC X(7) VALUE 'DM1099Z'.
               10  FILLER           PIC X(7) VALUE 'DM11BBZ'.
               10  FILLER           PIC X(7) VALUE 'DM11BB1'.
               10  FILLER           PIC X(7) VALUE 'DM11BCZ'.
               10  FILLER           PIC X(7) VALUE 'DM11BYZ'.
               10  FILLER           PIC X(7) VALUE 'DM11B6Z'.
               10  FILLER           PIC X(7) VALUE 'DM11B7Z'.
               10  FILLER           PIC X(7) VALUE 'DM11B8Z'.
               10  FILLER           PIC X(7) VALUE 'DM11B9Z'.
               10  FILLER           PIC X(7) VALUE 'DM119BZ'.
               10  FILLER           PIC X(7) VALUE 'DM119CZ'.
               10  FILLER           PIC X(7) VALUE 'DM119YZ'.
               10  FILLER           PIC X(7) VALUE 'DM1197Z'.
               10  FILLER           PIC X(7) VALUE 'DM1198Z'.
               10  FILLER           PIC X(7) VALUE 'DM1199Z'.
               10  FILLER           PIC X(7) VALUE 'DPYBFZZ'.
               10  FILLER           PIC X(7) VALUE 'DPYB7ZZ'.
               10  FILLER           PIC X(7) VALUE 'DPYB8ZZ'.
               10  FILLER           PIC X(7) VALUE 'DPYCFZZ'.
               10  FILLER           PIC X(7) VALUE 'DPYC7ZZ'.
               10  FILLER           PIC X(7) VALUE 'DPYC8ZZ'.
               10  FILLER           PIC X(7) VALUE 'DPY0FZZ'.
               10  FILLER           PIC X(7) VALUE 'DPY07ZZ'.
               10  FILLER           PIC X(7) VALUE 'DPY08ZZ'.
               10  FILLER           PIC X(7) VALUE 'DPY2FZZ'.
               10  FILLER           PIC X(7) VALUE 'DPY27ZZ'.
               10  FILLER           PIC X(7) VALUE 'DPY28ZZ'.
               10  FILLER           PIC X(7) VALUE 'DPY3FZZ'.
               10  FILLER           PIC X(7) VALUE 'DPY37ZZ'.
               10  FILLER           PIC X(7) VALUE 'DPY38ZZ'.
               10  FILLER           PIC X(7) VALUE 'DPY4FZZ'.
               10  FILLER           PIC X(7) VALUE 'DPY47ZZ'.
               10  FILLER           PIC X(7) VALUE 'DPY48ZZ'.
               10  FILLER           PIC X(7) VALUE 'DPY5FZZ'.
               10  FILLER           PIC X(7) VALUE 'DPY57ZZ'.
               10  FILLER           PIC X(7) VALUE 'DPY58ZZ'.
               10  FILLER           PIC X(7) VALUE 'DPY6FZZ'.
               10  FILLER           PIC X(7) VALUE 'DPY67ZZ'.
               10  FILLER           PIC X(7) VALUE 'DPY68ZZ'.
               10  FILLER           PIC X(7) VALUE 'DPY7FZZ'.
               10  FILLER           PIC X(7) VALUE 'DPY77ZZ'.
               10  FILLER           PIC X(7) VALUE 'DPY78ZZ'.
               10  FILLER           PIC X(7) VALUE 'DPY8FZZ'.
               10  FILLER           PIC X(7) VALUE 'DPY87ZZ'.
               10  FILLER           PIC X(7) VALUE 'DPY88ZZ'.
               10  FILLER           PIC X(7) VALUE 'DPY9FZZ'.
               10  FILLER           PIC X(7) VALUE 'DPY97ZZ'.
               10  FILLER           PIC X(7) VALUE 'DPY98ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP0B0ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP0B1ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP0B2ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP0B3ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP0B3Z0'.
               10  FILLER           PIC X(7) VALUE 'DP0B4ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP0B5ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP0B6ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP0C0ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP0C1ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP0C2ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP0C3ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP0C3Z0'.
               10  FILLER           PIC X(7) VALUE 'DP0C4ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP0C5ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP0C6ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP000ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP001ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP002ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP003ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP003Z0'.
               10  FILLER           PIC X(7) VALUE 'DP004ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP005ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP006ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP020ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP021ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP022ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP023ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP023Z0'.
               10  FILLER           PIC X(7) VALUE 'DP024ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP025ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP026ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP030ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP031ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP032ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP033ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP033Z0'.
               10  FILLER           PIC X(7) VALUE 'DP034ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP035ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP036ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP040ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP041ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP042ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP043ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP043Z0'.
               10  FILLER           PIC X(7) VALUE 'DP044ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP045ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP046ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP050ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP051ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP052ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP053ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP053Z0'.
               10  FILLER           PIC X(7) VALUE 'DP054ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP055ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP056ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP060ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP061ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP062ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP063ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP063Z0'.
               10  FILLER           PIC X(7) VALUE 'DP064ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP065ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP066ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP070ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP071ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP072ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP073ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP073Z0'.
               10  FILLER           PIC X(7) VALUE 'DP074ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP075ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP076ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP080ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP081ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP082ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP083ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP083Z0'.
               10  FILLER           PIC X(7) VALUE 'DP084ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP085ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP086ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP090ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP091ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP092ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP093ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP093Z0'.
               10  FILLER           PIC X(7) VALUE 'DP094ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP095ZZ'.
               10  FILLER           PIC X(7) VALUE 'DP096ZZ'.
               10  FILLER           PIC X(7) VALUE 'DTY0CZZ'.
               10  FILLER           PIC X(7) VALUE 'DTY0FZZ'.
               10  FILLER           PIC X(7) VALUE 'DTY07ZZ'.
               10  FILLER           PIC X(7) VALUE 'DTY08ZZ'.
               10  FILLER           PIC X(7) VALUE 'DTY1CZZ'.
               10  FILLER           PIC X(7) VALUE 'DTY1FZZ'.
               10  FILLER           PIC X(7) VALUE 'DTY17ZZ'.
               10  FILLER           PIC X(7) VALUE 'DTY18ZZ'.
               10  FILLER           PIC X(7) VALUE 'DTY2CZZ'.
               10  FILLER           PIC X(7) VALUE 'DTY2FZZ'.
               10  FILLER           PIC X(7) VALUE 'DTY27ZZ'.
               10  FILLER           PIC X(7) VALUE 'DTY28ZZ'.
               10  FILLER           PIC X(7) VALUE 'DTY3CZZ'.
               10  FILLER           PIC X(7) VALUE 'DTY3FZZ'.
               10  FILLER           PIC X(7) VALUE 'DTY37ZZ'.
               10  FILLER           PIC X(7) VALUE 'DTY38ZZ'.
               10  FILLER           PIC X(7) VALUE 'DT000ZZ'.
               10  FILLER           PIC X(7) VALUE 'DT001ZZ'.
               10  FILLER           PIC X(7) VALUE 'DT002ZZ'.
               10  FILLER           PIC X(7) VALUE 'DT003ZZ'.
               10  FILLER           PIC X(7) VALUE 'DT003Z0'.
               10  FILLER           PIC X(7) VALUE 'DT004ZZ'.
               10  FILLER           PIC X(7) VALUE 'DT005ZZ'.
               10  FILLER           PIC X(7) VALUE 'DT006ZZ'.
               10  FILLER           PIC X(7) VALUE 'DT010ZZ'.
               10  FILLER           PIC X(7) VALUE 'DT011ZZ'.
               10  FILLER           PIC X(7) VALUE 'DT012ZZ'.
               10  FILLER           PIC X(7) VALUE 'DT013ZZ'.
               10  FILLER           PIC X(7) VALUE 'DT013Z0'.
               10  FILLER           PIC X(7) VALUE 'DT014ZZ'.
               10  FILLER           PIC X(7) VALUE 'DT015ZZ'.
               10  FILLER           PIC X(7) VALUE 'DT016ZZ'.
               10  FILLER           PIC X(7) VALUE 'DT020ZZ'.
               10  FILLER           PIC X(7) VALUE 'DT021ZZ'.
               10  FILLER           PIC X(7) VALUE 'DT022ZZ'.
               10  FILLER           PIC X(7) VALUE 'DT023ZZ'.
               10  FILLER           PIC X(7) VALUE 'DT023Z0'.
               10  FILLER           PIC X(7) VALUE 'DT024ZZ'.
               10  FILLER           PIC X(7) VALUE 'DT025ZZ'.
               10  FILLER           PIC X(7) VALUE 'DT026ZZ'.
               10  FILLER           PIC X(7) VALUE 'DT030ZZ'.
               10  FILLER           PIC X(7) VALUE 'DT031ZZ'.
               10  FILLER           PIC X(7) VALUE 'DT032ZZ'.
               10  FILLER           PIC X(7) VALUE 'DT033ZZ'.
               10  FILLER           PIC X(7) VALUE 'DT033Z0'.
               10  FILLER           PIC X(7) VALUE 'DT034ZZ'.
               10  FILLER           PIC X(7) VALUE 'DT035ZZ'.
               10  FILLER           PIC X(7) VALUE 'DT036ZZ'.
               10  FILLER           PIC X(7) VALUE 'DT10BBZ'.
               10  FILLER           PIC X(7) VALUE 'DT10BB1'.
               10  FILLER           PIC X(7) VALUE 'DT10BCZ'.
               10  FILLER           PIC X(7) VALUE 'DT10BYZ'.
               10  FILLER           PIC X(7) VALUE 'DT10B6Z'.
               10  FILLER           PIC X(7) VALUE 'DT10B7Z'.
               10  FILLER           PIC X(7) VALUE 'DT10B8Z'.
               10  FILLER           PIC X(7) VALUE 'DT10B9Z'.
               10  FILLER           PIC X(7) VALUE 'DT109BZ'.
               10  FILLER           PIC X(7) VALUE 'DT109CZ'.
               10  FILLER           PIC X(7) VALUE 'DT109YZ'.
               10  FILLER           PIC X(7) VALUE 'DT1097Z'.
               10  FILLER           PIC X(7) VALUE 'DT1098Z'.
               10  FILLER           PIC X(7) VALUE 'DT1099Z'.
               10  FILLER           PIC X(7) VALUE 'DT11BBZ'.
               10  FILLER           PIC X(7) VALUE 'DT11BB1'.
               10  FILLER           PIC X(7) VALUE 'DT11BCZ'.
               10  FILLER           PIC X(7) VALUE 'DT11BYZ'.
               10  FILLER           PIC X(7) VALUE 'DT11B6Z'.
               10  FILLER           PIC X(7) VALUE 'DT11B7Z'.
               10  FILLER           PIC X(7) VALUE 'DT11B8Z'.
               10  FILLER           PIC X(7) VALUE 'DT11B9Z'.
               10  FILLER           PIC X(7) VALUE 'DT119BZ'.
               10  FILLER           PIC X(7) VALUE 'DT119CZ'.
               10  FILLER           PIC X(7) VALUE 'DT119YZ'.
               10  FILLER           PIC X(7) VALUE 'DT1197Z'.
               10  FILLER           PIC X(7) VALUE 'DT1198Z'.
               10  FILLER           PIC X(7) VALUE 'DT1199Z'.
               10  FILLER           PIC X(7) VALUE 'DT12BBZ'.
               10  FILLER           PIC X(7) VALUE 'DT12BB1'.
               10  FILLER           PIC X(7) VALUE 'DT12BCZ'.
               10  FILLER           PIC X(7) VALUE 'DT12BYZ'.
               10  FILLER           PIC X(7) VALUE 'DT12B6Z'.
               10  FILLER           PIC X(7) VALUE 'DT12B7Z'.
               10  FILLER           PIC X(7) VALUE 'DT12B8Z'.
               10  FILLER           PIC X(7) VALUE 'DT12B9Z'.
               10  FILLER           PIC X(7) VALUE 'DT129BZ'.
               10  FILLER           PIC X(7) VALUE 'DT129CZ'.
               10  FILLER           PIC X(7) VALUE 'DT129YZ'.
               10  FILLER           PIC X(7) VALUE 'DT1297Z'.
               10  FILLER           PIC X(7) VALUE 'DT1298Z'.
               10  FILLER           PIC X(7) VALUE 'DT1299Z'.
               10  FILLER           PIC X(7) VALUE 'DT13BBZ'.
               10  FILLER           PIC X(7) VALUE 'DT13BB1'.
               10  FILLER           PIC X(7) VALUE 'DT13BCZ'.
               10  FILLER           PIC X(7) VALUE 'DT13BYZ'.
               10  FILLER           PIC X(7) VALUE 'DT13B6Z'.
               10  FILLER           PIC X(7) VALUE 'DT13B7Z'.
               10  FILLER           PIC X(7) VALUE 'DT13B8Z'.
               10  FILLER           PIC X(7) VALUE 'DT13B9Z'.
               10  FILLER           PIC X(7) VALUE 'DT139BZ'.
               10  FILLER           PIC X(7) VALUE 'DT139CZ'.
               10  FILLER           PIC X(7) VALUE 'DT139YZ'.
               10  FILLER           PIC X(7) VALUE 'DT1397Z'.
               10  FILLER           PIC X(7) VALUE 'DT1398Z'.
               10  FILLER           PIC X(7) VALUE 'DT1399Z'.
               10  FILLER           PIC X(7) VALUE 'DUY0CZZ'.
               10  FILLER           PIC X(7) VALUE 'DUY0FZZ'.
               10  FILLER           PIC X(7) VALUE 'DUY07ZZ'.
               10  FILLER           PIC X(7) VALUE 'DUY08ZZ'.
               10  FILLER           PIC X(7) VALUE 'DUY1CZZ'.
               10  FILLER           PIC X(7) VALUE 'DUY1FZZ'.
               10  FILLER           PIC X(7) VALUE 'DUY17ZZ'.
               10  FILLER           PIC X(7) VALUE 'DUY18ZZ'.
               10  FILLER           PIC X(7) VALUE 'DUY2CZZ'.
               10  FILLER           PIC X(7) VALUE 'DUY2FZZ'.
               10  FILLER           PIC X(7) VALUE 'DUY27ZZ'.
               10  FILLER           PIC X(7) VALUE 'DUY28ZZ'.
               10  FILLER           PIC X(7) VALUE 'DU000ZZ'.
               10  FILLER           PIC X(7) VALUE 'DU001ZZ'.
               10  FILLER           PIC X(7) VALUE 'DU002ZZ'.
               10  FILLER           PIC X(7) VALUE 'DU003ZZ'.
               10  FILLER           PIC X(7) VALUE 'DU003Z0'.
               10  FILLER           PIC X(7) VALUE 'DU004ZZ'.
               10  FILLER           PIC X(7) VALUE 'DU005ZZ'.
               10  FILLER           PIC X(7) VALUE 'DU006ZZ'.
               10  FILLER           PIC X(7) VALUE 'DU010ZZ'.
               10  FILLER           PIC X(7) VALUE 'DU011ZZ'.
               10  FILLER           PIC X(7) VALUE 'DU012ZZ'.
               10  FILLER           PIC X(7) VALUE 'DU013ZZ'.
               10  FILLER           PIC X(7) VALUE 'DU013Z0'.
               10  FILLER           PIC X(7) VALUE 'DU014ZZ'.
               10  FILLER           PIC X(7) VALUE 'DU015ZZ'.
               10  FILLER           PIC X(7) VALUE 'DU016ZZ'.
               10  FILLER           PIC X(7) VALUE 'DU020ZZ'.
               10  FILLER           PIC X(7) VALUE 'DU021ZZ'.
               10  FILLER           PIC X(7) VALUE 'DU022ZZ'.
               10  FILLER           PIC X(7) VALUE 'DU023ZZ'.
               10  FILLER           PIC X(7) VALUE 'DU023Z0'.
               10  FILLER           PIC X(7) VALUE 'DU024ZZ'.
               10  FILLER           PIC X(7) VALUE 'DU025ZZ'.
               10  FILLER           PIC X(7) VALUE 'DU026ZZ'.
               10  FILLER           PIC X(7) VALUE 'DU10BBZ'.
               10  FILLER           PIC X(7) VALUE 'DU10BB1'.
               10  FILLER           PIC X(7) VALUE 'DU10BCZ'.
               10  FILLER           PIC X(7) VALUE 'DU10BYZ'.
               10  FILLER           PIC X(7) VALUE 'DU10B6Z'.
               10  FILLER           PIC X(7) VALUE 'DU10B7Z'.
               10  FILLER           PIC X(7) VALUE 'DU10B8Z'.
               10  FILLER           PIC X(7) VALUE 'DU10B9Z'.
               10  FILLER           PIC X(7) VALUE 'DU109BZ'.
               10  FILLER           PIC X(7) VALUE 'DU109CZ'.
               10  FILLER           PIC X(7) VALUE 'DU109YZ'.
               10  FILLER           PIC X(7) VALUE 'DU1097Z'.
               10  FILLER           PIC X(7) VALUE 'DU1098Z'.
               10  FILLER           PIC X(7) VALUE 'DU1099Z'.
               10  FILLER           PIC X(7) VALUE 'DU11BBZ'.
               10  FILLER           PIC X(7) VALUE 'DU11BB1'.
               10  FILLER           PIC X(7) VALUE 'DU11BCZ'.
               10  FILLER           PIC X(7) VALUE 'DU11BYZ'.
               10  FILLER           PIC X(7) VALUE 'DU11B6Z'.
               10  FILLER           PIC X(7) VALUE 'DU11B7Z'.
               10  FILLER           PIC X(7) VALUE 'DU11B8Z'.
               10  FILLER           PIC X(7) VALUE 'DU11B9Z'.
               10  FILLER           PIC X(7) VALUE 'DU119BZ'.
               10  FILLER           PIC X(7) VALUE 'DU119CZ'.
               10  FILLER           PIC X(7) VALUE 'DU119YZ'.
               10  FILLER           PIC X(7) VALUE 'DU1197Z'.
               10  FILLER           PIC X(7) VALUE 'DU1198Z'.
               10  FILLER           PIC X(7) VALUE 'DU1199Z'.
               10  FILLER           PIC X(7) VALUE 'DU12BBZ'.
               10  FILLER           PIC X(7) VALUE 'DU12BB1'.
               10  FILLER           PIC X(7) VALUE 'DU12BCZ'.
               10  FILLER           PIC X(7) VALUE 'DU12BYZ'.
               10  FILLER           PIC X(7) VALUE 'DU12B6Z'.
               10  FILLER           PIC X(7) VALUE 'DU12B7Z'.
               10  FILLER           PIC X(7) VALUE 'DU12B8Z'.
               10  FILLER           PIC X(7) VALUE 'DU12B9Z'.
               10  FILLER           PIC X(7) VALUE 'DU129BZ'.
               10  FILLER           PIC X(7) VALUE 'DU129CZ'.
               10  FILLER           PIC X(7) VALUE 'DU129YZ'.
               10  FILLER           PIC X(7) VALUE 'DU1297Z'.
               10  FILLER           PIC X(7) VALUE 'DU1298Z'.
               10  FILLER           PIC X(7) VALUE 'DU1299Z'.
               10  FILLER           PIC X(7) VALUE 'DVY0CZZ'.
               10  FILLER           PIC X(7) VALUE 'DVY0FZZ'.
               10  FILLER           PIC X(7) VALUE 'DVY0KZZ'.
               10  FILLER           PIC X(7) VALUE 'DVY07ZZ'.
               10  FILLER           PIC X(7) VALUE 'DVY08ZZ'.
               10  FILLER           PIC X(7) VALUE 'DVY1FZZ'.
               10  FILLER           PIC X(7) VALUE 'DVY17ZZ'.
               10  FILLER           PIC X(7) VALUE 'DVY18ZZ'.
               10  FILLER           PIC X(7) VALUE 'DV000ZZ'.
               10  FILLER           PIC X(7) VALUE 'DV001ZZ'.
               10  FILLER           PIC X(7) VALUE 'DV002ZZ'.
               10  FILLER           PIC X(7) VALUE 'DV003ZZ'.
               10  FILLER           PIC X(7) VALUE 'DV003Z0'.
               10  FILLER           PIC X(7) VALUE 'DV004ZZ'.
               10  FILLER           PIC X(7) VALUE 'DV005ZZ'.
               10  FILLER           PIC X(7) VALUE 'DV006ZZ'.
               10  FILLER           PIC X(7) VALUE 'DV010ZZ'.
               10  FILLER           PIC X(7) VALUE 'DV011ZZ'.
               10  FILLER           PIC X(7) VALUE 'DV012ZZ'.
               10  FILLER           PIC X(7) VALUE 'DV013ZZ'.
               10  FILLER           PIC X(7) VALUE 'DV013Z0'.
               10  FILLER           PIC X(7) VALUE 'DV014ZZ'.
               10  FILLER           PIC X(7) VALUE 'DV015ZZ'.
               10  FILLER           PIC X(7) VALUE 'DV016ZZ'.
               10  FILLER           PIC X(7) VALUE 'DV10BBZ'.
               10  FILLER           PIC X(7) VALUE 'DV10BB1'.
               10  FILLER           PIC X(7) VALUE 'DV10BCZ'.
               10  FILLER           PIC X(7) VALUE 'DV10BYZ'.
               10  FILLER           PIC X(7) VALUE 'DV10B6Z'.
               10  FILLER           PIC X(7) VALUE 'DV10B7Z'.
               10  FILLER           PIC X(7) VALUE 'DV10B8Z'.
               10  FILLER           PIC X(7) VALUE 'DV10B9Z'.
               10  FILLER           PIC X(7) VALUE 'DV109BZ'.
               10  FILLER           PIC X(7) VALUE 'DV109CZ'.
               10  FILLER           PIC X(7) VALUE 'DV109YZ'.
               10  FILLER           PIC X(7) VALUE 'DV1097Z'.
               10  FILLER           PIC X(7) VALUE 'DV1098Z'.
               10  FILLER           PIC X(7) VALUE 'DV1099Z'.
               10  FILLER           PIC X(7) VALUE 'DV11BBZ'.
               10  FILLER           PIC X(7) VALUE 'DV11BB1'.
               10  FILLER           PIC X(7) VALUE 'DV11BCZ'.
               10  FILLER           PIC X(7) VALUE 'DV11BYZ'.
               10  FILLER           PIC X(7) VALUE 'DV11B6Z'.
               10  FILLER           PIC X(7) VALUE 'DV11B7Z'.
               10  FILLER           PIC X(7) VALUE 'DV11B8Z'.
               10  FILLER           PIC X(7) VALUE 'DV11B9Z'.
               10  FILLER           PIC X(7) VALUE 'DV119BZ'.
               10  FILLER           PIC X(7) VALUE 'DV119CZ'.
               10  FILLER           PIC X(7) VALUE 'DV119YZ'.
               10  FILLER           PIC X(7) VALUE 'DV1197Z'.
               10  FILLER           PIC X(7) VALUE 'DV1198Z'.
               10  FILLER           PIC X(7) VALUE 'DV1199Z'.
               10  FILLER           PIC X(7) VALUE 'DWY1FZZ'.
               10  FILLER           PIC X(7) VALUE 'DWY17ZZ'.
               10  FILLER           PIC X(7) VALUE 'DWY18ZZ'.
               10  FILLER           PIC X(7) VALUE 'DWY2FZZ'.
               10  FILLER           PIC X(7) VALUE 'DWY27ZZ'.
               10  FILLER           PIC X(7) VALUE 'DWY28ZZ'.
               10  FILLER           PIC X(7) VALUE 'DWY3FZZ'.
               10  FILLER           PIC X(7) VALUE 'DWY37ZZ'.
               10  FILLER           PIC X(7) VALUE 'DWY38ZZ'.
               10  FILLER           PIC X(7) VALUE 'DWY4FZZ'.
               10  FILLER           PIC X(7) VALUE 'DWY47ZZ'.
               10  FILLER           PIC X(7) VALUE 'DWY48ZZ'.
               10  FILLER           PIC X(7) VALUE 'DWY5FZZ'.
               10  FILLER           PIC X(7) VALUE 'DWY5GDZ'.
               10  FILLER           PIC X(7) VALUE 'DWY5GFZ'.
               10  FILLER           PIC X(7) VALUE 'DWY5GGZ'.
               10  FILLER           PIC X(7) VALUE 'DWY5GHZ'.
               10  FILLER           PIC X(7) VALUE 'DWY5GYZ'.
               10  FILLER           PIC X(7) VALUE 'DWY57ZZ'.
               10  FILLER           PIC X(7) VALUE 'DWY58ZZ'.
               10  FILLER           PIC X(7) VALUE 'DWY6FZZ'.
               10  FILLER           PIC X(7) VALUE 'DWY67ZZ'.
               10  FILLER           PIC X(7) VALUE 'DWY68ZZ'.
               10  FILLER           PIC X(7) VALUE 'DW010ZZ'.
               10  FILLER           PIC X(7) VALUE 'DW011ZZ'.
               10  FILLER           PIC X(7) VALUE 'DW012ZZ'.
               10  FILLER           PIC X(7) VALUE 'DW013ZZ'.
               10  FILLER           PIC X(7) VALUE 'DW013Z0'.
               10  FILLER           PIC X(7) VALUE 'DW014ZZ'.
               10  FILLER           PIC X(7) VALUE 'DW015ZZ'.
               10  FILLER           PIC X(7) VALUE 'DW016ZZ'.
               10  FILLER           PIC X(7) VALUE 'DW020ZZ'.
               10  FILLER           PIC X(7) VALUE 'DW021ZZ'.
               10  FILLER           PIC X(7) VALUE 'DW022ZZ'.
               10  FILLER           PIC X(7) VALUE 'DW023ZZ'.
               10  FILLER           PIC X(7) VALUE 'DW023Z0'.
               10  FILLER           PIC X(7) VALUE 'DW024ZZ'.
               10  FILLER           PIC X(7) VALUE 'DW025ZZ'.
               10  FILLER           PIC X(7) VALUE 'DW026ZZ'.
               10  FILLER           PIC X(7) VALUE 'DW030ZZ'.
               10  FILLER           PIC X(7) VALUE 'DW031ZZ'.
               10  FILLER           PIC X(7) VALUE 'DW032ZZ'.
               10  FILLER           PIC X(7) VALUE 'DW033ZZ'.
               10  FILLER           PIC X(7) VALUE 'DW033Z0'.
               10  FILLER           PIC X(7) VALUE 'DW034ZZ'.
               10  FILLER           PIC X(7) VALUE 'DW035ZZ'.
               10  FILLER           PIC X(7) VALUE 'DW036ZZ'.
               10  FILLER           PIC X(7) VALUE 'DW040ZZ'.
               10  FILLER           PIC X(7) VALUE 'DW041ZZ'.
               10  FILLER           PIC X(7) VALUE 'DW042ZZ'.
               10  FILLER           PIC X(7) VALUE 'DW043ZZ'.
               10  FILLER           PIC X(7) VALUE 'DW043Z0'.
               10  FILLER           PIC X(7) VALUE 'DW044ZZ'.
               10  FILLER           PIC X(7) VALUE 'DW045ZZ'.
               10  FILLER           PIC X(7) VALUE 'DW046ZZ'.
               10  FILLER           PIC X(7) VALUE 'DW050ZZ'.
               10  FILLER           PIC X(7) VALUE 'DW051ZZ'.
               10  FILLER           PIC X(7) VALUE 'DW052ZZ'.
               10  FILLER           PIC X(7) VALUE 'DW053ZZ'.
               10  FILLER           PIC X(7) VALUE 'DW053Z0'.
               10  FILLER           PIC X(7) VALUE 'DW054ZZ'.
               10  FILLER           PIC X(7) VALUE 'DW055ZZ'.
               10  FILLER           PIC X(7) VALUE 'DW056ZZ'.
               10  FILLER           PIC X(7) VALUE 'DW060ZZ'.
               10  FILLER           PIC X(7) VALUE 'DW061ZZ'.
               10  FILLER           PIC X(7) VALUE 'DW062ZZ'.
               10  FILLER           PIC X(7) VALUE 'DW063ZZ'.
               10  FILLER           PIC X(7) VALUE 'DW063Z0'.
               10  FILLER           PIC X(7) VALUE 'DW064ZZ'.
               10  FILLER           PIC X(7) VALUE 'DW065ZZ'.
               10  FILLER           PIC X(7) VALUE 'DW066ZZ'.
               10  FILLER           PIC X(7) VALUE 'DW1KBBZ'.
               10  FILLER           PIC X(7) VALUE 'DW1KBB1'.
               10  FILLER           PIC X(7) VALUE 'DW1LBBZ'.
               10  FILLER           PIC X(7) VALUE 'DW1LBB1'.
               10  FILLER           PIC X(7) VALUE 'DW1PBBZ'.
               10  FILLER           PIC X(7) VALUE 'DW1PBB1'.
               10  FILLER           PIC X(7) VALUE 'DW1QBBZ'.
               10  FILLER           PIC X(7) VALUE 'DW1QBB1'.
               10  FILLER           PIC X(7) VALUE 'DW1RBBZ'.
               10  FILLER           PIC X(7) VALUE 'DW1RBB1'.
               10  FILLER           PIC X(7) VALUE 'DW1XBBZ'.
               10  FILLER           PIC X(7) VALUE 'DW1XBB1'.
               10  FILLER           PIC X(7) VALUE 'DW1YBBZ'.
               10  FILLER           PIC X(7) VALUE 'DW1YBB1'.
               10  FILLER           PIC X(7) VALUE 'DW10BBZ'.
               10  FILLER           PIC X(7) VALUE 'DW10BB1'.
               10  FILLER           PIC X(7) VALUE 'DW11BBZ'.
               10  FILLER           PIC X(7) VALUE 'DW11BB1'.
               10  FILLER           PIC X(7) VALUE 'DW11BCZ'.
               10  FILLER           PIC X(7) VALUE 'DW11BYZ'.
               10  FILLER           PIC X(7) VALUE 'DW11B6Z'.
               10  FILLER           PIC X(7) VALUE 'DW11B7Z'.
               10  FILLER           PIC X(7) VALUE 'DW11B8Z'.
               10  FILLER           PIC X(7) VALUE 'DW11B9Z'.
               10  FILLER           PIC X(7) VALUE 'DW119BZ'.
               10  FILLER           PIC X(7) VALUE 'DW119CZ'.
               10  FILLER           PIC X(7) VALUE 'DW119YZ'.
               10  FILLER           PIC X(7) VALUE 'DW1197Z'.
               10  FILLER           PIC X(7) VALUE 'DW1198Z'.
               10  FILLER           PIC X(7) VALUE 'DW1199Z'.
               10  FILLER           PIC X(7) VALUE 'DW12BBZ'.
               10  FILLER           PIC X(7) VALUE 'DW12BB1'.
               10  FILLER           PIC X(7) VALUE 'DW12BCZ'.
               10  FILLER           PIC X(7) VALUE 'DW12BYZ'.
               10  FILLER           PIC X(7) VALUE 'DW12B6Z'.
               10  FILLER           PIC X(7) VALUE 'DW12B7Z'.
               10  FILLER           PIC X(7) VALUE 'DW12B8Z'.
               10  FILLER           PIC X(7) VALUE 'DW12B9Z'.
               10  FILLER           PIC X(7) VALUE 'DW129BZ'.
               10  FILLER           PIC X(7) VALUE 'DW129CZ'.
               10  FILLER           PIC X(7) VALUE 'DW129YZ'.
               10  FILLER           PIC X(7) VALUE 'DW1297Z'.
               10  FILLER           PIC X(7) VALUE 'DW1298Z'.
               10  FILLER           PIC X(7) VALUE 'DW1299Z'.
               10  FILLER           PIC X(7) VALUE 'DW13BBZ'.
               10  FILLER           PIC X(7) VALUE 'DW13BB1'.
               10  FILLER           PIC X(7) VALUE 'DW13BCZ'.
               10  FILLER           PIC X(7) VALUE 'DW13BYZ'.
               10  FILLER           PIC X(7) VALUE 'DW13B6Z'.
               10  FILLER           PIC X(7) VALUE 'DW13B7Z'.
               10  FILLER           PIC X(7) VALUE 'DW13B8Z'.
               10  FILLER           PIC X(7) VALUE 'DW13B9Z'.
               10  FILLER           PIC X(7) VALUE 'DW139BZ'.
               10  FILLER           PIC X(7) VALUE 'DW139CZ'.
               10  FILLER           PIC X(7) VALUE 'DW139YZ'.
               10  FILLER           PIC X(7) VALUE 'DW1397Z'.
               10  FILLER           PIC X(7) VALUE 'DW1398Z'.
               10  FILLER           PIC X(7) VALUE 'DW1399Z'.
               10  FILLER           PIC X(7) VALUE 'DW16BBZ'.
               10  FILLER           PIC X(7) VALUE 'DW16BB1'.
               10  FILLER           PIC X(7) VALUE 'DW16BCZ'.
               10  FILLER           PIC X(7) VALUE 'DW16BYZ'.
               10  FILLER           PIC X(7) VALUE 'DW16B6Z'.
               10  FILLER           PIC X(7) VALUE 'DW16B7Z'.
               10  FILLER           PIC X(7) VALUE 'DW16B8Z'.
               10  FILLER           PIC X(7) VALUE 'DW16B9Z'.
               10  FILLER           PIC X(7) VALUE 'DW169BZ'.
               10  FILLER           PIC X(7) VALUE 'DW169CZ'.
               10  FILLER           PIC X(7) VALUE 'DW169YZ'.
               10  FILLER           PIC X(7) VALUE 'DW1697Z'.
               10  FILLER           PIC X(7) VALUE 'DW1698Z'.
               10  FILLER           PIC X(7) VALUE 'DW1699Z'.
               10  FILLER           PIC X(7) VALUE 'D0Y0CZZ'.
               10  FILLER           PIC X(7) VALUE 'D0Y0FZZ'.
               10  FILLER           PIC X(7) VALUE 'D0Y0KZZ'.
               10  FILLER           PIC X(7) VALUE 'D0Y07ZZ'.
               10  FILLER           PIC X(7) VALUE 'D0Y08ZZ'.
               10  FILLER           PIC X(7) VALUE 'D0Y1CZZ'.
               10  FILLER           PIC X(7) VALUE 'D0Y1FZZ'.
               10  FILLER           PIC X(7) VALUE 'D0Y1KZZ'.
               10  FILLER           PIC X(7) VALUE 'D0Y17ZZ'.
               10  FILLER           PIC X(7) VALUE 'D0Y18ZZ'.
               10  FILLER           PIC X(7) VALUE 'D0Y6CZZ'.
               10  FILLER           PIC X(7) VALUE 'D0Y6FZZ'.
               10  FILLER           PIC X(7) VALUE 'D0Y6KZZ'.
               10  FILLER           PIC X(7) VALUE 'D0Y67ZZ'.
               10  FILLER           PIC X(7) VALUE 'D0Y68ZZ'.
               10  FILLER           PIC X(7) VALUE 'D0Y7CZZ'.
               10  FILLER           PIC X(7) VALUE 'D0Y7FZZ'.
               10  FILLER           PIC X(7) VALUE 'D0Y7KZZ'.
               10  FILLER           PIC X(7) VALUE 'D0Y77ZZ'.
               10  FILLER           PIC X(7) VALUE 'D0Y78ZZ'.
               10  FILLER           PIC X(7) VALUE 'D0000ZZ'.
               10  FILLER           PIC X(7) VALUE 'D0001ZZ'.
               10  FILLER           PIC X(7) VALUE 'D0002ZZ'.
               10  FILLER           PIC X(7) VALUE 'D0003ZZ'.
               10  FILLER           PIC X(7) VALUE 'D0003Z0'.
               10  FILLER           PIC X(7) VALUE 'D0004ZZ'.
               10  FILLER           PIC X(7) VALUE 'D0005ZZ'.
               10  FILLER           PIC X(7) VALUE 'D0006ZZ'.
               10  FILLER           PIC X(7) VALUE 'D0010ZZ'.
               10  FILLER           PIC X(7) VALUE 'D0011ZZ'.
               10  FILLER           PIC X(7) VALUE 'D0012ZZ'.
               10  FILLER           PIC X(7) VALUE 'D0013ZZ'.
               10  FILLER           PIC X(7) VALUE 'D0013Z0'.
               10  FILLER           PIC X(7) VALUE 'D0014ZZ'.
               10  FILLER           PIC X(7) VALUE 'D0015ZZ'.
               10  FILLER           PIC X(7) VALUE 'D0016ZZ'.
               10  FILLER           PIC X(7) VALUE 'D0060ZZ'.
               10  FILLER           PIC X(7) VALUE 'D0061ZZ'.
               10  FILLER           PIC X(7) VALUE 'D0062ZZ'.
               10  FILLER           PIC X(7) VALUE 'D0063ZZ'.
               10  FILLER           PIC X(7) VALUE 'D0063Z0'.
               10  FILLER           PIC X(7) VALUE 'D0064ZZ'.
               10  FILLER           PIC X(7) VALUE 'D0065ZZ'.
               10  FILLER           PIC X(7) VALUE 'D0066ZZ'.
               10  FILLER           PIC X(7) VALUE 'D0070ZZ'.
               10  FILLER           PIC X(7) VALUE 'D0071ZZ'.
               10  FILLER           PIC X(7) VALUE 'D0072ZZ'.
               10  FILLER           PIC X(7) VALUE 'D0073ZZ'.
               10  FILLER           PIC X(7) VALUE 'D0073Z0'.
               10  FILLER           PIC X(7) VALUE 'D0074ZZ'.
               10  FILLER           PIC X(7) VALUE 'D0075ZZ'.
               10  FILLER           PIC X(7) VALUE 'D0076ZZ'.
               10  FILLER           PIC X(7) VALUE 'D010BBZ'.
               10  FILLER           PIC X(7) VALUE 'D010BB1'.
               10  FILLER           PIC X(7) VALUE 'D010BCZ'.
               10  FILLER           PIC X(7) VALUE 'D010BYZ'.
               10  FILLER           PIC X(7) VALUE 'D010B6Z'.
               10  FILLER           PIC X(7) VALUE 'D010B7Z'.
               10  FILLER           PIC X(7) VALUE 'D010B8Z'.
               10  FILLER           PIC X(7) VALUE 'D010B9Z'.
               10  FILLER           PIC X(7) VALUE 'D0109BZ'.
               10  FILLER           PIC X(7) VALUE 'D0109CZ'.
               10  FILLER           PIC X(7) VALUE 'D0109YZ'.
               10  FILLER           PIC X(7) VALUE 'D01097Z'.
               10  FILLER           PIC X(7) VALUE 'D01098Z'.
               10  FILLER           PIC X(7) VALUE 'D01099Z'.
               10  FILLER           PIC X(7) VALUE 'D011BBZ'.
               10  FILLER           PIC X(7) VALUE 'D011BB1'.
               10  FILLER           PIC X(7) VALUE 'D011BCZ'.
               10  FILLER           PIC X(7) VALUE 'D011BYZ'.
               10  FILLER           PIC X(7) VALUE 'D011B6Z'.
               10  FILLER           PIC X(7) VALUE 'D011B7Z'.
               10  FILLER           PIC X(7) VALUE 'D011B8Z'.
               10  FILLER           PIC X(7) VALUE 'D011B9Z'.
               10  FILLER           PIC X(7) VALUE 'D0119BZ'.
               10  FILLER           PIC X(7) VALUE 'D0119CZ'.
               10  FILLER           PIC X(7) VALUE 'D0119YZ'.
               10  FILLER           PIC X(7) VALUE 'D01197Z'.
               10  FILLER           PIC X(7) VALUE 'D01198Z'.
               10  FILLER           PIC X(7) VALUE 'D01199Z'.
               10  FILLER           PIC X(7) VALUE 'D016BBZ'.
               10  FILLER           PIC X(7) VALUE 'D016BB1'.
               10  FILLER           PIC X(7) VALUE 'D016BCZ'.
               10  FILLER           PIC X(7) VALUE 'D016BYZ'.
               10  FILLER           PIC X(7) VALUE 'D016B6Z'.
               10  FILLER           PIC X(7) VALUE 'D016B7Z'.
               10  FILLER           PIC X(7) VALUE 'D016B8Z'.
               10  FILLER           PIC X(7) VALUE 'D016B9Z'.
               10  FILLER           PIC X(7) VALUE 'D0169BZ'.
               10  FILLER           PIC X(7) VALUE 'D0169CZ'.
               10  FILLER           PIC X(7) VALUE 'D0169YZ'.
               10  FILLER           PIC X(7) VALUE 'D01697Z'.
               10  FILLER           PIC X(7) VALUE 'D01698Z'.
               10  FILLER           PIC X(7) VALUE 'D01699Z'.
               10  FILLER           PIC X(7) VALUE 'D017BBZ'.
               10  FILLER           PIC X(7) VALUE 'D017BB1'.
               10  FILLER           PIC X(7) VALUE 'D017BCZ'.
               10  FILLER           PIC X(7) VALUE 'D017BYZ'.
               10  FILLER           PIC X(7) VALUE 'D017B6Z'.
               10  FILLER           PIC X(7) VALUE 'D017B7Z'.
               10  FILLER           PIC X(7) VALUE 'D017B8Z'.
               10  FILLER           PIC X(7) VALUE 'D017B9Z'.
               10  FILLER           PIC X(7) VALUE 'D0179BZ'.
               10  FILLER           PIC X(7) VALUE 'D0179CZ'.
               10  FILLER           PIC X(7) VALUE 'D0179YZ'.
               10  FILLER           PIC X(7) VALUE 'D01797Z'.
               10  FILLER           PIC X(7) VALUE 'D01798Z'.
               10  FILLER           PIC X(7) VALUE 'D01799Z'.
               10  FILLER           PIC X(7) VALUE 'D7Y0FZZ'.
               10  FILLER           PIC X(7) VALUE 'D7Y08ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7Y1FZZ'.
               10  FILLER           PIC X(7) VALUE 'D7Y18ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7Y2FZZ'.
               10  FILLER           PIC X(7) VALUE 'D7Y28ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7Y3FZZ'.
               10  FILLER           PIC X(7) VALUE 'D7Y38ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7Y4FZZ'.
               10  FILLER           PIC X(7) VALUE 'D7Y48ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7Y5FZZ'.
               10  FILLER           PIC X(7) VALUE 'D7Y58ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7Y6FZZ'.
               10  FILLER           PIC X(7) VALUE 'D7Y68ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7Y7FZZ'.
               10  FILLER           PIC X(7) VALUE 'D7Y78ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7Y8FZZ'.
               10  FILLER           PIC X(7) VALUE 'D7Y88ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7000ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7001ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7002ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7003ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7003Z0'.
               10  FILLER           PIC X(7) VALUE 'D7004ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7005ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7006ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7010ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7011ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7012ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7013ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7013Z0'.
               10  FILLER           PIC X(7) VALUE 'D7014ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7015ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7016ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7020ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7021ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7022ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7023ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7023Z0'.
               10  FILLER           PIC X(7) VALUE 'D7024ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7025ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7026ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7030ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7031ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7032ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7033ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7033Z0'.
               10  FILLER           PIC X(7) VALUE 'D7034ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7035ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7036ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7040ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7041ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7042ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7043ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7043Z0'.
               10  FILLER           PIC X(7) VALUE 'D7044ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7045ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7046ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7050ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7051ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7052ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7053ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7053Z0'.
               10  FILLER           PIC X(7) VALUE 'D7054ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7055ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7056ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7060ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7061ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7062ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7063ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7063Z0'.
               10  FILLER           PIC X(7) VALUE 'D7064ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7065ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7066ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7070ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7071ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7072ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7073ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7073Z0'.
               10  FILLER           PIC X(7) VALUE 'D7074ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7075ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7076ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7080ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7081ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7082ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7083ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7083Z0'.
               10  FILLER           PIC X(7) VALUE 'D7084ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7085ZZ'.
               10  FILLER           PIC X(7) VALUE 'D7086ZZ'.
               10  FILLER           PIC X(7) VALUE 'D710BBZ'.
               10  FILLER           PIC X(7) VALUE 'D710BB1'.
               10  FILLER           PIC X(7) VALUE 'D710BCZ'.
               10  FILLER           PIC X(7) VALUE 'D710BYZ'.
               10  FILLER           PIC X(7) VALUE 'D710B6Z'.
               10  FILLER           PIC X(7) VALUE 'D710B7Z'.
               10  FILLER           PIC X(7) VALUE 'D710B8Z'.
               10  FILLER           PIC X(7) VALUE 'D710B9Z'.
               10  FILLER           PIC X(7) VALUE 'D7109BZ'.
               10  FILLER           PIC X(7) VALUE 'D7109CZ'.
               10  FILLER           PIC X(7) VALUE 'D7109YZ'.
               10  FILLER           PIC X(7) VALUE 'D71097Z'.
               10  FILLER           PIC X(7) VALUE 'D71098Z'.
               10  FILLER           PIC X(7) VALUE 'D71099Z'.
               10  FILLER           PIC X(7) VALUE 'D711BBZ'.
               10  FILLER           PIC X(7) VALUE 'D711BB1'.
               10  FILLER           PIC X(7) VALUE 'D711BCZ'.
               10  FILLER           PIC X(7) VALUE 'D711BYZ'.
               10  FILLER           PIC X(7) VALUE 'D711B6Z'.
               10  FILLER           PIC X(7) VALUE 'D711B7Z'.
               10  FILLER           PIC X(7) VALUE 'D711B8Z'.
               10  FILLER           PIC X(7) VALUE 'D711B9Z'.
               10  FILLER           PIC X(7) VALUE 'D7119BZ'.
               10  FILLER           PIC X(7) VALUE 'D7119CZ'.
               10  FILLER           PIC X(7) VALUE 'D7119YZ'.
               10  FILLER           PIC X(7) VALUE 'D71197Z'.
               10  FILLER           PIC X(7) VALUE 'D71198Z'.
               10  FILLER           PIC X(7) VALUE 'D71199Z'.
               10  FILLER           PIC X(7) VALUE 'D712BBZ'.
               10  FILLER           PIC X(7) VALUE 'D712BB1'.
               10  FILLER           PIC X(7) VALUE 'D712BCZ'.
               10  FILLER           PIC X(7) VALUE 'D712BYZ'.
               10  FILLER           PIC X(7) VALUE 'D712B6Z'.
               10  FILLER           PIC X(7) VALUE 'D712B7Z'.
               10  FILLER           PIC X(7) VALUE 'D712B8Z'.
               10  FILLER           PIC X(7) VALUE 'D712B9Z'.
               10  FILLER           PIC X(7) VALUE 'D7129BZ'.
               10  FILLER           PIC X(7) VALUE 'D7129CZ'.
               10  FILLER           PIC X(7) VALUE 'D7129YZ'.
               10  FILLER           PIC X(7) VALUE 'D71297Z'.
               10  FILLER           PIC X(7) VALUE 'D71298Z'.
               10  FILLER           PIC X(7) VALUE 'D71299Z'.
               10  FILLER           PIC X(7) VALUE 'D713BBZ'.
               10  FILLER           PIC X(7) VALUE 'D713BB1'.
               10  FILLER           PIC X(7) VALUE 'D713BCZ'.
               10  FILLER           PIC X(7) VALUE 'D713BYZ'.
               10  FILLER           PIC X(7) VALUE 'D713B6Z'.
               10  FILLER           PIC X(7) VALUE 'D713B7Z'.
               10  FILLER           PIC X(7) VALUE 'D713B8Z'.
               10  FILLER           PIC X(7) VALUE 'D713B9Z'.
               10  FILLER           PIC X(7) VALUE 'D7139BZ'.
               10  FILLER           PIC X(7) VALUE 'D7139CZ'.
               10  FILLER           PIC X(7) VALUE 'D7139YZ'.
               10  FILLER           PIC X(7) VALUE 'D71397Z'.
               10  FILLER           PIC X(7) VALUE 'D71398Z'.
               10  FILLER           PIC X(7) VALUE 'D71399Z'.
               10  FILLER           PIC X(7) VALUE 'D714BBZ'.
               10  FILLER           PIC X(7) VALUE 'D714BB1'.
               10  FILLER           PIC X(7) VALUE 'D714BCZ'.
               10  FILLER           PIC X(7) VALUE 'D714BYZ'.
               10  FILLER           PIC X(7) VALUE 'D714B6Z'.
               10  FILLER           PIC X(7) VALUE 'D714B7Z'.
               10  FILLER           PIC X(7) VALUE 'D714B8Z'.
               10  FILLER           PIC X(7) VALUE 'D714B9Z'.
               10  FILLER           PIC X(7) VALUE 'D7149BZ'.
               10  FILLER           PIC X(7) VALUE 'D7149CZ'.
               10  FILLER           PIC X(7) VALUE 'D7149YZ'.
               10  FILLER           PIC X(7) VALUE 'D71497Z'.
               10  FILLER           PIC X(7) VALUE 'D71498Z'.
               10  FILLER           PIC X(7) VALUE 'D71499Z'.
               10  FILLER           PIC X(7) VALUE 'D715BBZ'.
               10  FILLER           PIC X(7) VALUE 'D715BB1'.
               10  FILLER           PIC X(7) VALUE 'D715BCZ'.
               10  FILLER           PIC X(7) VALUE 'D715BYZ'.
               10  FILLER           PIC X(7) VALUE 'D715B6Z'.
               10  FILLER           PIC X(7) VALUE 'D715B7Z'.
               10  FILLER           PIC X(7) VALUE 'D715B8Z'.
               10  FILLER           PIC X(7) VALUE 'D715B9Z'.
               10  FILLER           PIC X(7) VALUE 'D7159BZ'.
               10  FILLER           PIC X(7) VALUE 'D7159CZ'.
               10  FILLER           PIC X(7) VALUE 'D7159YZ'.
               10  FILLER           PIC X(7) VALUE 'D71597Z'.
               10  FILLER           PIC X(7) VALUE 'D71598Z'.
               10  FILLER           PIC X(7) VALUE 'D71599Z'.
               10  FILLER           PIC X(7) VALUE 'D716BBZ'.
               10  FILLER           PIC X(7) VALUE 'D716BB1'.
               10  FILLER           PIC X(7) VALUE 'D716BCZ'.
               10  FILLER           PIC X(7) VALUE 'D716BYZ'.
               10  FILLER           PIC X(7) VALUE 'D716B6Z'.
               10  FILLER           PIC X(7) VALUE 'D716B7Z'.
               10  FILLER           PIC X(7) VALUE 'D716B8Z'.
               10  FILLER           PIC X(7) VALUE 'D716B9Z'.
               10  FILLER           PIC X(7) VALUE 'D7169BZ'.
               10  FILLER           PIC X(7) VALUE 'D7169CZ'.
               10  FILLER           PIC X(7) VALUE 'D7169YZ'.
               10  FILLER           PIC X(7) VALUE 'D71697Z'.
               10  FILLER           PIC X(7) VALUE 'D71698Z'.
               10  FILLER           PIC X(7) VALUE 'D71699Z'.
               10  FILLER           PIC X(7) VALUE 'D717BBZ'.
               10  FILLER           PIC X(7) VALUE 'D717BB1'.
               10  FILLER           PIC X(7) VALUE 'D717BCZ'.
               10  FILLER           PIC X(7) VALUE 'D717BYZ'.
               10  FILLER           PIC X(7) VALUE 'D717B6Z'.
               10  FILLER           PIC X(7) VALUE 'D717B7Z'.
               10  FILLER           PIC X(7) VALUE 'D717B8Z'.
               10  FILLER           PIC X(7) VALUE 'D717B9Z'.
               10  FILLER           PIC X(7) VALUE 'D7179BZ'.
               10  FILLER           PIC X(7) VALUE 'D7179CZ'.
               10  FILLER           PIC X(7) VALUE 'D7179YZ'.
               10  FILLER           PIC X(7) VALUE 'D71797Z'.
               10  FILLER           PIC X(7) VALUE 'D71798Z'.
               10  FILLER           PIC X(7) VALUE 'D71799Z'.
               10  FILLER           PIC X(7) VALUE 'D718BBZ'.
               10  FILLER           PIC X(7) VALUE 'D718BB1'.
               10  FILLER           PIC X(7) VALUE 'D718BCZ'.
               10  FILLER           PIC X(7) VALUE 'D718BYZ'.
               10  FILLER           PIC X(7) VALUE 'D718B6Z'.
               10  FILLER           PIC X(7) VALUE 'D718B7Z'.
               10  FILLER           PIC X(7) VALUE 'D718B8Z'.
               10  FILLER           PIC X(7) VALUE 'D718B9Z'.
               10  FILLER           PIC X(7) VALUE 'D7189BZ'.
               10  FILLER           PIC X(7) VALUE 'D7189CZ'.
               10  FILLER           PIC X(7) VALUE 'D7189YZ'.
               10  FILLER           PIC X(7) VALUE 'D71897Z'.
               10  FILLER           PIC X(7) VALUE 'D71898Z'.
               10  FILLER           PIC X(7) VALUE 'D71899Z'.
               10  FILLER           PIC X(7) VALUE 'D8Y0FZZ'.
               10  FILLER           PIC X(7) VALUE 'D8Y07ZZ'.
               10  FILLER           PIC X(7) VALUE 'D8Y08ZZ'.
               10  FILLER           PIC X(7) VALUE 'D8000ZZ'.
               10  FILLER           PIC X(7) VALUE 'D8001ZZ'.
               10  FILLER           PIC X(7) VALUE 'D8002ZZ'.
               10  FILLER           PIC X(7) VALUE 'D8003ZZ'.
               10  FILLER           PIC X(7) VALUE 'D8003Z0'.
               10  FILLER           PIC X(7) VALUE 'D8004ZZ'.
               10  FILLER           PIC X(7) VALUE 'D8005ZZ'.
               10  FILLER           PIC X(7) VALUE 'D8006ZZ'.
               10  FILLER           PIC X(7) VALUE 'D810BBZ'.
               10  FILLER           PIC X(7) VALUE 'D810BB1'.
               10  FILLER           PIC X(7) VALUE 'D810BCZ'.
               10  FILLER           PIC X(7) VALUE 'D810BYZ'.
               10  FILLER           PIC X(7) VALUE 'D810B6Z'.
               10  FILLER           PIC X(7) VALUE 'D810B7Z'.
               10  FILLER           PIC X(7) VALUE 'D810B8Z'.
               10  FILLER           PIC X(7) VALUE 'D810B9Z'.
               10  FILLER           PIC X(7) VALUE 'D8109BZ'.
               10  FILLER           PIC X(7) VALUE 'D8109CZ'.
               10  FILLER           PIC X(7) VALUE 'D8109YZ'.
               10  FILLER           PIC X(7) VALUE 'D81097Z'.
               10  FILLER           PIC X(7) VALUE 'D81098Z'.
               10  FILLER           PIC X(7) VALUE 'D81099Z'.
               10  FILLER           PIC X(7) VALUE 'D9YBCZZ'.
               10  FILLER           PIC X(7) VALUE 'D9YBFZZ'.
               10  FILLER           PIC X(7) VALUE 'D9YB7ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9YB8ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9YCCZZ'.
               10  FILLER           PIC X(7) VALUE 'D9YCFZZ'.
               10  FILLER           PIC X(7) VALUE 'D9YDCZZ'.
               10  FILLER           PIC X(7) VALUE 'D9YDFZZ'.
               10  FILLER           PIC X(7) VALUE 'D9YD7ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9YD8ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9YF7ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9YF8ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9Y0FZZ'.
               10  FILLER           PIC X(7) VALUE 'D9Y07ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9Y08ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9Y1FZZ'.
               10  FILLER           PIC X(7) VALUE 'D9Y17ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9Y18ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9Y37ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9Y38ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9Y4CZZ'.
               10  FILLER           PIC X(7) VALUE 'D9Y4FZZ'.
               10  FILLER           PIC X(7) VALUE 'D9Y47ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9Y48ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9Y5FZZ'.
               10  FILLER           PIC X(7) VALUE 'D9Y57ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9Y58ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9Y6FZZ'.
               10  FILLER           PIC X(7) VALUE 'D9Y67ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9Y68ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9Y7FZZ'.
               10  FILLER           PIC X(7) VALUE 'D9Y77ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9Y78ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9Y8FZZ'.
               10  FILLER           PIC X(7) VALUE 'D9Y87ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9Y88ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9Y9FZZ'.
               10  FILLER           PIC X(7) VALUE 'D9Y97ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9Y98ZZ'.
               10  FILLER           PIC X(7) VALUE 'D90B0ZZ'.
               10  FILLER           PIC X(7) VALUE 'D90B1ZZ'.
               10  FILLER           PIC X(7) VALUE 'D90B2ZZ'.
               10  FILLER           PIC X(7) VALUE 'D90B3ZZ'.
               10  FILLER           PIC X(7) VALUE 'D90B3Z0'.
               10  FILLER           PIC X(7) VALUE 'D90B4ZZ'.
               10  FILLER           PIC X(7) VALUE 'D90B5ZZ'.
               10  FILLER           PIC X(7) VALUE 'D90B6ZZ'.
               10  FILLER           PIC X(7) VALUE 'D90D0ZZ'.
               10  FILLER           PIC X(7) VALUE 'D90D1ZZ'.
               10  FILLER           PIC X(7) VALUE 'D90D2ZZ'.
               10  FILLER           PIC X(7) VALUE 'D90D3ZZ'.
               10  FILLER           PIC X(7) VALUE 'D90D3Z0'.
               10  FILLER           PIC X(7) VALUE 'D90D4ZZ'.
               10  FILLER           PIC X(7) VALUE 'D90D5ZZ'.
               10  FILLER           PIC X(7) VALUE 'D90D6ZZ'.
               10  FILLER           PIC X(7) VALUE 'D90F0ZZ'.
               10  FILLER           PIC X(7) VALUE 'D90F1ZZ'.
               10  FILLER           PIC X(7) VALUE 'D90F2ZZ'.
               10  FILLER           PIC X(7) VALUE 'D90F3ZZ'.
               10  FILLER           PIC X(7) VALUE 'D90F3Z0'.
               10  FILLER           PIC X(7) VALUE 'D90F4ZZ'.
               10  FILLER           PIC X(7) VALUE 'D90F5ZZ'.
               10  FILLER           PIC X(7) VALUE 'D90F6ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9000ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9001ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9002ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9003ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9003Z0'.
               10  FILLER           PIC X(7) VALUE 'D9004ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9005ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9006ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9010ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9011ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9012ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9013ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9013Z0'.
               10  FILLER           PIC X(7) VALUE 'D9014ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9015ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9016ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9030ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9031ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9032ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9033ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9033Z0'.
               10  FILLER           PIC X(7) VALUE 'D9034ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9035ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9036ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9040ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9041ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9042ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9043ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9043Z0'.
               10  FILLER           PIC X(7) VALUE 'D9044ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9045ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9046ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9050ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9051ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9052ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9053ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9053Z0'.
               10  FILLER           PIC X(7) VALUE 'D9054ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9055ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9056ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9060ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9061ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9062ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9063ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9063Z0'.
               10  FILLER           PIC X(7) VALUE 'D9064ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9065ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9066ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9070ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9071ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9072ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9073ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9073Z0'.
               10  FILLER           PIC X(7) VALUE 'D9074ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9075ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9076ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9080ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9081ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9082ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9083ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9083Z0'.
               10  FILLER           PIC X(7) VALUE 'D9084ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9085ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9086ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9090ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9091ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9092ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9093ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9093Z0'.
               10  FILLER           PIC X(7) VALUE 'D9094ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9095ZZ'.
               10  FILLER           PIC X(7) VALUE 'D9096ZZ'.
               10  FILLER           PIC X(7) VALUE 'D91BBBZ'.
               10  FILLER           PIC X(7) VALUE 'D91BBB1'.
               10  FILLER           PIC X(7) VALUE 'D91BBCZ'.
               10  FILLER           PIC X(7) VALUE 'D91BBYZ'.
               10  FILLER           PIC X(7) VALUE 'D91BB6Z'.
               10  FILLER           PIC X(7) VALUE 'D91BB7Z'.
               10  FILLER           PIC X(7) VALUE 'D91BB8Z'.
               10  FILLER           PIC X(7) VALUE 'D91BB9Z'.
               10  FILLER           PIC X(7) VALUE 'D91B9BZ'.
               10  FILLER           PIC X(7) VALUE 'D91B9CZ'.
               10  FILLER           PIC X(7) VALUE 'D91B9YZ'.
               10  FILLER           PIC X(7) VALUE 'D91B97Z'.
               10  FILLER           PIC X(7) VALUE 'D91B98Z'.
               10  FILLER           PIC X(7) VALUE 'D91B99Z'.
               10  FILLER           PIC X(7) VALUE 'D91DBBZ'.
               10  FILLER           PIC X(7) VALUE 'D91DBB1'.
               10  FILLER           PIC X(7) VALUE 'D91DBCZ'.
               10  FILLER           PIC X(7) VALUE 'D91DBYZ'.
               10  FILLER           PIC X(7) VALUE 'D91DB6Z'.
               10  FILLER           PIC X(7) VALUE 'D91DB7Z'.
               10  FILLER           PIC X(7) VALUE 'D91DB8Z'.
               10  FILLER           PIC X(7) VALUE 'D91DB9Z'.
               10  FILLER           PIC X(7) VALUE 'D91D9BZ'.
               10  FILLER           PIC X(7) VALUE 'D91D9CZ'.
               10  FILLER           PIC X(7) VALUE 'D91D9YZ'.
               10  FILLER           PIC X(7) VALUE 'D91D97Z'.
               10  FILLER           PIC X(7) VALUE 'D91D98Z'.
               10  FILLER           PIC X(7) VALUE 'D91D99Z'.
               10  FILLER           PIC X(7) VALUE 'D91FBBZ'.
               10  FILLER           PIC X(7) VALUE 'D91FBB1'.
               10  FILLER           PIC X(7) VALUE 'D91FBCZ'.
               10  FILLER           PIC X(7) VALUE 'D91FBYZ'.
               10  FILLER           PIC X(7) VALUE 'D91FB6Z'.
               10  FILLER           PIC X(7) VALUE 'D91FB7Z'.
               10  FILLER           PIC X(7) VALUE 'D91FB8Z'.
               10  FILLER           PIC X(7) VALUE 'D91FB9Z'.
               10  FILLER           PIC X(7) VALUE 'D91F9BZ'.
               10  FILLER           PIC X(7) VALUE 'D91F9CZ'.
               10  FILLER           PIC X(7) VALUE 'D91F9YZ'.
               10  FILLER           PIC X(7) VALUE 'D91F97Z'.
               10  FILLER           PIC X(7) VALUE 'D91F98Z'.
               10  FILLER           PIC X(7) VALUE 'D91F99Z'.
               10  FILLER           PIC X(7) VALUE 'D910BBZ'.
               10  FILLER           PIC X(7) VALUE 'D910BB1'.
               10  FILLER           PIC X(7) VALUE 'D910BCZ'.
               10  FILLER           PIC X(7) VALUE 'D910BYZ'.
               10  FILLER           PIC X(7) VALUE 'D910B6Z'.
               10  FILLER           PIC X(7) VALUE 'D910B7Z'.
               10  FILLER           PIC X(7) VALUE 'D910B8Z'.
               10  FILLER           PIC X(7) VALUE 'D910B9Z'.
               10  FILLER           PIC X(7) VALUE 'D9109BZ'.
               10  FILLER           PIC X(7) VALUE 'D9109CZ'.
               10  FILLER           PIC X(7) VALUE 'D9109YZ'.
               10  FILLER           PIC X(7) VALUE 'D91097Z'.
               10  FILLER           PIC X(7) VALUE 'D91098Z'.
               10  FILLER           PIC X(7) VALUE 'D91099Z'.
               10  FILLER           PIC X(7) VALUE 'D911BBZ'.
               10  FILLER           PIC X(7) VALUE 'D911BB1'.
               10  FILLER           PIC X(7) VALUE 'D911BCZ'.
               10  FILLER           PIC X(7) VALUE 'D911BYZ'.
               10  FILLER           PIC X(7) VALUE 'D911B6Z'.
               10  FILLER           PIC X(7) VALUE 'D911B7Z'.
               10  FILLER           PIC X(7) VALUE 'D911B8Z'.
               10  FILLER           PIC X(7) VALUE 'D911B9Z'.
               10  FILLER           PIC X(7) VALUE 'D9119BZ'.
               10  FILLER           PIC X(7) VALUE 'D9119CZ'.
               10  FILLER           PIC X(7) VALUE 'D9119YZ'.
               10  FILLER           PIC X(7) VALUE 'D91197Z'.
               10  FILLER           PIC X(7) VALUE 'D91198Z'.
               10  FILLER           PIC X(7) VALUE 'D91199Z'.
               10  FILLER           PIC X(7) VALUE 'D913BBZ'.
               10  FILLER           PIC X(7) VALUE 'D913BB1'.
               10  FILLER           PIC X(7) VALUE 'D913BCZ'.
               10  FILLER           PIC X(7) VALUE 'D913BYZ'.
               10  FILLER           PIC X(7) VALUE 'D913B6Z'.
               10  FILLER           PIC X(7) VALUE 'D913B7Z'.
               10  FILLER           PIC X(7) VALUE 'D913B8Z'.
               10  FILLER           PIC X(7) VALUE 'D913B9Z'.
               10  FILLER           PIC X(7) VALUE 'D9139BZ'.
               10  FILLER           PIC X(7) VALUE 'D9139CZ'.
               10  FILLER           PIC X(7) VALUE 'D9139YZ'.
               10  FILLER           PIC X(7) VALUE 'D91397Z'.
               10  FILLER           PIC X(7) VALUE 'D91398Z'.
               10  FILLER           PIC X(7) VALUE 'D91399Z'.
               10  FILLER           PIC X(7) VALUE 'D914BBZ'.
               10  FILLER           PIC X(7) VALUE 'D914BB1'.
               10  FILLER           PIC X(7) VALUE 'D914BCZ'.
               10  FILLER           PIC X(7) VALUE 'D914BYZ'.
               10  FILLER           PIC X(7) VALUE 'D914B6Z'.
               10  FILLER           PIC X(7) VALUE 'D914B7Z'.
               10  FILLER           PIC X(7) VALUE 'D914B8Z'.
               10  FILLER           PIC X(7) VALUE 'D914B9Z'.
               10  FILLER           PIC X(7) VALUE 'D9149BZ'.
               10  FILLER           PIC X(7) VALUE 'D9149CZ'.
               10  FILLER           PIC X(7) VALUE 'D9149YZ'.
               10  FILLER           PIC X(7) VALUE 'D91497Z'.
               10  FILLER           PIC X(7) VALUE 'D91498Z'.
               10  FILLER           PIC X(7) VALUE 'D91499Z'.
               10  FILLER           PIC X(7) VALUE 'D915BBZ'.
               10  FILLER           PIC X(7) VALUE 'D915BB1'.
               10  FILLER           PIC X(7) VALUE 'D915BCZ'.
               10  FILLER           PIC X(7) VALUE 'D915BYZ'.
               10  FILLER           PIC X(7) VALUE 'D915B6Z'.
               10  FILLER           PIC X(7) VALUE 'D915B7Z'.
               10  FILLER           PIC X(7) VALUE 'D915B8Z'.
               10  FILLER           PIC X(7) VALUE 'D915B9Z'.
               10  FILLER           PIC X(7) VALUE 'D9159BZ'.
               10  FILLER           PIC X(7) VALUE 'D9159CZ'.
               10  FILLER           PIC X(7) VALUE 'D9159YZ'.
               10  FILLER           PIC X(7) VALUE 'D91597Z'.
               10  FILLER           PIC X(7) VALUE 'D91598Z'.
               10  FILLER           PIC X(7) VALUE 'D91599Z'.
               10  FILLER           PIC X(7) VALUE 'D916BBZ'.
               10  FILLER           PIC X(7) VALUE 'D916BB1'.
               10  FILLER           PIC X(7) VALUE 'D916BCZ'.
               10  FILLER           PIC X(7) VALUE 'D916BYZ'.
               10  FILLER           PIC X(7) VALUE 'D916B6Z'.
               10  FILLER           PIC X(7) VALUE 'D916B7Z'.
               10  FILLER           PIC X(7) VALUE 'D916B8Z'.
               10  FILLER           PIC X(7) VALUE 'D916B9Z'.
               10  FILLER           PIC X(7) VALUE 'D9169BZ'.
               10  FILLER           PIC X(7) VALUE 'D9169CZ'.
               10  FILLER           PIC X(7) VALUE 'D9169YZ'.
               10  FILLER           PIC X(7) VALUE 'D91697Z'.
               10  FILLER           PIC X(7) VALUE 'D91698Z'.
               10  FILLER           PIC X(7) VALUE 'D91699Z'.
               10  FILLER           PIC X(7) VALUE 'D917BBZ'.
               10  FILLER           PIC X(7) VALUE 'D917BB1'.
               10  FILLER           PIC X(7) VALUE 'D917BCZ'.
               10  FILLER           PIC X(7) VALUE 'D917BYZ'.
               10  FILLER           PIC X(7) VALUE 'D917B6Z'.
               10  FILLER           PIC X(7) VALUE 'D917B7Z'.
               10  FILLER           PIC X(7) VALUE 'D917B8Z'.
               10  FILLER           PIC X(7) VALUE 'D917B9Z'.
               10  FILLER           PIC X(7) VALUE 'D9179BZ'.
               10  FILLER           PIC X(7) VALUE 'D9179CZ'.
               10  FILLER           PIC X(7) VALUE 'D9179YZ'.
               10  FILLER           PIC X(7) VALUE 'D91797Z'.
               10  FILLER           PIC X(7) VALUE 'D91798Z'.
               10  FILLER           PIC X(7) VALUE 'D91799Z'.
               10  FILLER           PIC X(7) VALUE 'D918BBZ'.
               10  FILLER           PIC X(7) VALUE 'D918BB1'.
               10  FILLER           PIC X(7) VALUE 'D918BCZ'.
               10  FILLER           PIC X(7) VALUE 'D918BYZ'.
               10  FILLER           PIC X(7) VALUE 'D918B6Z'.
               10  FILLER           PIC X(7) VALUE 'D918B7Z'.
               10  FILLER           PIC X(7) VALUE 'D918B8Z'.
               10  FILLER           PIC X(7) VALUE 'D918B9Z'.
               10  FILLER           PIC X(7) VALUE 'D9189BZ'.
               10  FILLER           PIC X(7) VALUE 'D9189CZ'.
               10  FILLER           PIC X(7) VALUE 'D9189YZ'.
               10  FILLER           PIC X(7) VALUE 'D91897Z'.
               10  FILLER           PIC X(7) VALUE 'D91898Z'.
               10  FILLER           PIC X(7) VALUE 'D91899Z'.
               10  FILLER           PIC X(7) VALUE 'D919BBZ'.
               10  FILLER           PIC X(7) VALUE 'D919BB1'.
               10  FILLER           PIC X(7) VALUE 'D919BCZ'.
               10  FILLER           PIC X(7) VALUE 'D919BYZ'.
               10  FILLER           PIC X(7) VALUE 'D919B6Z'.
               10  FILLER           PIC X(7) VALUE 'D919B7Z'.
               10  FILLER           PIC X(7) VALUE 'D919B8Z'.
               10  FILLER           PIC X(7) VALUE 'D919B9Z'.
               10  FILLER           PIC X(7) VALUE 'D9199BZ'.
               10  FILLER           PIC X(7) VALUE 'D9199CZ'.
               10  FILLER           PIC X(7) VALUE 'D9199YZ'.
               10  FILLER           PIC X(7) VALUE 'D91997Z'.
               10  FILLER           PIC X(7) VALUE 'D91998Z'.
               10  FILLER           PIC X(7) VALUE 'D91999Z'.
               10  FILLER           PIC X(7) VALUE 'XW0DXJ5'.
               10  FILLER           PIC X(7) VALUE 'XW0DXL5'.
               10  FILLER           PIC X(7) VALUE 'XW0DXR5'.
               10  FILLER           PIC X(7) VALUE 'XW0DXV5'.
               10  FILLER           PIC X(7) VALUE 'XW033B3'.
               10  FILLER           PIC X(7) VALUE 'XW033D6'.
               10  FILLER           PIC X(7) VALUE 'XW033Q5'.
               10  FILLER           PIC X(7) VALUE 'XW033S5'.
               10  FILLER           PIC X(7) VALUE 'XW03336'.
               10  FILLER           PIC X(7) VALUE 'XW043B3'.
               10  FILLER           PIC X(7) VALUE 'XW043D6'.
               10  FILLER           PIC X(7) VALUE 'XW043Q5'.
               10  FILLER           PIC X(7) VALUE 'XW043S5'.
               10  FILLER           PIC X(7) VALUE 'XW04336'.
               10  FILLER           PIC X(7) VALUE '0BHK01Z'.
               10  FILLER           PIC X(7) VALUE '0BHK31Z'.
               10  FILLER           PIC X(7) VALUE '0BHK41Z'.
               10  FILLER           PIC X(7) VALUE '0BHK71Z'.
               10  FILLER           PIC X(7) VALUE '0BHK81Z'.
               10  FILLER           PIC X(7) VALUE '0BHL01Z'.
               10  FILLER           PIC X(7) VALUE '0BHL31Z'.
               10  FILLER           PIC X(7) VALUE '0BHL41Z'.
               10  FILLER           PIC X(7) VALUE '0BHL71Z'.
               10  FILLER           PIC X(7) VALUE '0BHL81Z'.
               10  FILLER           PIC X(7) VALUE '0BH001Z'.
               10  FILLER           PIC X(7) VALUE '0BH031Z'.
               10  FILLER           PIC X(7) VALUE '0BH041Z'.
               10  FILLER           PIC X(7) VALUE '0BH071Z'.
               10  FILLER           PIC X(7) VALUE '0BH081Z'.
               10  FILLER           PIC X(7) VALUE '0CHA01Z'.
               10  FILLER           PIC X(7) VALUE '0CHA31Z'.
               10  FILLER           PIC X(7) VALUE '0CHA71Z'.
               10  FILLER           PIC X(7) VALUE '0CHA81Z'.
               10  FILLER           PIC X(7) VALUE '0CHS01Z'.
               10  FILLER           PIC X(7) VALUE '0CHS31Z'.
               10  FILLER           PIC X(7) VALUE '0CHS71Z'.
               10  FILLER           PIC X(7) VALUE '0CHS81Z'.
               10  FILLER           PIC X(7) VALUE '0CHY01Z'.
               10  FILLER           PIC X(7) VALUE '0CHY31Z'.
               10  FILLER           PIC X(7) VALUE '0CHY71Z'.
               10  FILLER           PIC X(7) VALUE '0CHY81Z'.
               10  FILLER           PIC X(7) VALUE '0CH7X1Z'.
               10  FILLER           PIC X(7) VALUE '0CH701Z'.
               10  FILLER           PIC X(7) VALUE '0CH731Z'.
               10  FILLER           PIC X(7) VALUE '0DHA01Z'.
               10  FILLER           PIC X(7) VALUE '0DHA31Z'.
               10  FILLER           PIC X(7) VALUE '0DHA41Z'.
               10  FILLER           PIC X(7) VALUE '0DHA71Z'.
               10  FILLER           PIC X(7) VALUE '0DHA81Z'.
               10  FILLER           PIC X(7) VALUE '0DHB01Z'.
               10  FILLER           PIC X(7) VALUE '0DHB31Z'.
               10  FILLER           PIC X(7) VALUE '0DHB41Z'.
               10  FILLER           PIC X(7) VALUE '0DHB71Z'.
               10  FILLER           PIC X(7) VALUE '0DHB81Z'.
               10  FILLER           PIC X(7) VALUE '0DHE01Z'.
               10  FILLER           PIC X(7) VALUE '0DHE31Z'.
               10  FILLER           PIC X(7) VALUE '0DHE41Z'.
               10  FILLER           PIC X(7) VALUE '0DHE71Z'.
               10  FILLER           PIC X(7) VALUE '0DHE81Z'.
               10  FILLER           PIC X(7) VALUE '0DHP01Z'.
               10  FILLER           PIC X(7) VALUE '0DHP31Z'.
               10  FILLER           PIC X(7) VALUE '0DHP41Z'.
               10  FILLER           PIC X(7) VALUE '0DHP71Z'.
               10  FILLER           PIC X(7) VALUE '0DHP81Z'.
               10  FILLER           PIC X(7) VALUE '0DH501Z'.
               10  FILLER           PIC X(7) VALUE '0DH531Z'.
               10  FILLER           PIC X(7) VALUE '0DH541Z'.
               10  FILLER           PIC X(7) VALUE '0DH571Z'.
               10  FILLER           PIC X(7) VALUE '0DH581Z'.
               10  FILLER           PIC X(7) VALUE '0DH601Z'.
               10  FILLER           PIC X(7) VALUE '0DH631Z'.
               10  FILLER           PIC X(7) VALUE '0DH641Z'.
               10  FILLER           PIC X(7) VALUE '0DH671Z'.
               10  FILLER           PIC X(7) VALUE '0DH681Z'.
               10  FILLER           PIC X(7) VALUE '0DH801Z'.
               10  FILLER           PIC X(7) VALUE '0DH831Z'.
               10  FILLER           PIC X(7) VALUE '0DH841Z'.
               10  FILLER           PIC X(7) VALUE '0DH871Z'.
               10  FILLER           PIC X(7) VALUE '0DH881Z'.
               10  FILLER           PIC X(7) VALUE '0DH901Z'.
               10  FILLER           PIC X(7) VALUE '0DH931Z'.
               10  FILLER           PIC X(7) VALUE '0DH941Z'.
               10  FILLER           PIC X(7) VALUE '0DH971Z'.
               10  FILLER           PIC X(7) VALUE '0DH981Z'.
               10  FILLER           PIC X(7) VALUE '0FHB01Z'.
               10  FILLER           PIC X(7) VALUE '0FHB31Z'.
               10  FILLER           PIC X(7) VALUE '0FHB41Z'.
               10  FILLER           PIC X(7) VALUE '0FHB71Z'.
               10  FILLER           PIC X(7) VALUE '0FHB81Z'.
               10  FILLER           PIC X(7) VALUE '0FHD01Z'.
               10  FILLER           PIC X(7) VALUE '0FHD31Z'.
               10  FILLER           PIC X(7) VALUE '0FHD41Z'.
               10  FILLER           PIC X(7) VALUE '0FHD71Z'.
               10  FILLER           PIC X(7) VALUE '0FHD81Z'.
               10  FILLER           PIC X(7) VALUE '0FHG01Z'.
               10  FILLER           PIC X(7) VALUE '0FHG31Z'.
               10  FILLER           PIC X(7) VALUE '0FHG41Z'.
               10  FILLER           PIC X(7) VALUE '0FH001Z'.
               10  FILLER           PIC X(7) VALUE '0FH031Z'.
               10  FILLER           PIC X(7) VALUE '0FH041Z'.
               10  FILLER           PIC X(7) VALUE '0FH401Z'.
               10  FILLER           PIC X(7) VALUE '0FH431Z'.
               10  FILLER           PIC X(7) VALUE '0FH441Z'.
               10  FILLER           PIC X(7) VALUE '0GHS01Z'.
               10  FILLER           PIC X(7) VALUE '0GHS31Z'.
               10  FILLER           PIC X(7) VALUE '0GHS41Z'.
               10  FILLER           PIC X(7) VALUE '0HHT01Z'.
               10  FILLER           PIC X(7) VALUE '0HHT31Z'.
               10  FILLER           PIC X(7) VALUE '0HHT71Z'.
               10  FILLER           PIC X(7) VALUE '0HHT81Z'.
               10  FILLER           PIC X(7) VALUE '0HHU01Z'.
               10  FILLER           PIC X(7) VALUE '0HHU31Z'.
               10  FILLER           PIC X(7) VALUE '0HHU71Z'.
               10  FILLER           PIC X(7) VALUE '0HHU81Z'.
               10  FILLER           PIC X(7) VALUE '0HHV01Z'.
               10  FILLER           PIC X(7) VALUE '0HHV31Z'.
               10  FILLER           PIC X(7) VALUE '0HHV71Z'.
               10  FILLER           PIC X(7) VALUE '0HHV81Z'.
               10  FILLER           PIC X(7) VALUE '0HHWX1Z'.
               10  FILLER           PIC X(7) VALUE '0HHW01Z'.
               10  FILLER           PIC X(7) VALUE '0HHW31Z'.
               10  FILLER           PIC X(7) VALUE '0HHW71Z'.
               10  FILLER           PIC X(7) VALUE '0HHW81Z'.
               10  FILLER           PIC X(7) VALUE '0HHXX1Z'.
               10  FILLER           PIC X(7) VALUE '0HHX01Z'.
               10  FILLER           PIC X(7) VALUE '0HHX31Z'.
               10  FILLER           PIC X(7) VALUE '0HHX71Z'.
               10  FILLER           PIC X(7) VALUE '0HHX81Z'.
               10  FILLER           PIC X(7) VALUE '0JHS01Z'.
               10  FILLER           PIC X(7) VALUE '0JHS31Z'.
               10  FILLER           PIC X(7) VALUE '0JHT01Z'.
               10  FILLER           PIC X(7) VALUE '0JHT31Z'.
               10  FILLER           PIC X(7) VALUE '0JHV01Z'.
               10  FILLER           PIC X(7) VALUE '0JHV31Z'.
               10  FILLER           PIC X(7) VALUE '0JHW01Z'.
               10  FILLER           PIC X(7) VALUE '0JHW31Z'.
               10  FILLER           PIC X(7) VALUE '0THB01Z'.
               10  FILLER           PIC X(7) VALUE '0THB31Z'.
               10  FILLER           PIC X(7) VALUE '0THB41Z'.
               10  FILLER           PIC X(7) VALUE '0THB71Z'.
               10  FILLER           PIC X(7) VALUE '0THB81Z'.
               10  FILLER           PIC X(7) VALUE '0THD01Z'.
               10  FILLER           PIC X(7) VALUE '0THD31Z'.
               10  FILLER           PIC X(7) VALUE '0THD41Z'.
               10  FILLER           PIC X(7) VALUE '0THD71Z'.
               10  FILLER           PIC X(7) VALUE '0THD81Z'.
               10  FILLER           PIC X(7) VALUE '0TH501Z'.
               10  FILLER           PIC X(7) VALUE '0TH531Z'.
               10  FILLER           PIC X(7) VALUE '0TH541Z'.
               10  FILLER           PIC X(7) VALUE '0TH571Z'.
               10  FILLER           PIC X(7) VALUE '0TH581Z'.
               10  FILLER           PIC X(7) VALUE '0TH901Z'.
               10  FILLER           PIC X(7) VALUE '0TH931Z'.
               10  FILLER           PIC X(7) VALUE '0TH941Z'.
               10  FILLER           PIC X(7) VALUE '0TH971Z'.
               10  FILLER           PIC X(7) VALUE '0TH981Z'.
               10  FILLER           PIC X(7) VALUE '0UHC01Z'.
               10  FILLER           PIC X(7) VALUE '0UHC31Z'.
               10  FILLER           PIC X(7) VALUE '0UHC41Z'.
               10  FILLER           PIC X(7) VALUE '0UHC71Z'.
               10  FILLER           PIC X(7) VALUE '0UHC81Z'.
               10  FILLER           PIC X(7) VALUE '0UHGX1Z'.
               10  FILLER           PIC X(7) VALUE '0UHG01Z'.
               10  FILLER           PIC X(7) VALUE '0UHG31Z'.
               10  FILLER           PIC X(7) VALUE '0UHG41Z'.
               10  FILLER           PIC X(7) VALUE '0UHG71Z'.
               10  FILLER           PIC X(7) VALUE '0UHG81Z'.
               10  FILLER           PIC X(7) VALUE '0UH301Z'.
               10  FILLER           PIC X(7) VALUE '0UH331Z'.
               10  FILLER           PIC X(7) VALUE '0UH341Z'.
               10  FILLER           PIC X(7) VALUE '0UH371Z'.
               10  FILLER           PIC X(7) VALUE '0UH381Z'.
               10  FILLER           PIC X(7) VALUE '0UH901Z'.
               10  FILLER           PIC X(7) VALUE '0UH971Z'.
               10  FILLER           PIC X(7) VALUE '0UH981Z'.
               10  FILLER           PIC X(7) VALUE '0VHD01Z'.
               10  FILLER           PIC X(7) VALUE '0VHD31Z'.
               10  FILLER           PIC X(7) VALUE '0VHD41Z'.
               10  FILLER           PIC X(7) VALUE '0VHD71Z'.
               10  FILLER           PIC X(7) VALUE '0VHD81Z'.
               10  FILLER           PIC X(7) VALUE '0VH001Z'.
               10  FILLER           PIC X(7) VALUE '0VH031Z'.
               10  FILLER           PIC X(7) VALUE '0VH041Z'.
               10  FILLER           PIC X(7) VALUE '0VH071Z'.
               10  FILLER           PIC X(7) VALUE '0VH081Z'.
               10  FILLER           PIC X(7) VALUE '0WHB01Z'.
               10  FILLER           PIC X(7) VALUE '0WHB31Z'.
               10  FILLER           PIC X(7) VALUE '0WHB41Z'.
               10  FILLER           PIC X(7) VALUE '0WHC01Z'.
               10  FILLER           PIC X(7) VALUE '0WHC31Z'.
               10  FILLER           PIC X(7) VALUE '0WHC41Z'.
               10  FILLER           PIC X(7) VALUE '0WHD01Z'.
               10  FILLER           PIC X(7) VALUE '0WHD31Z'.
               10  FILLER           PIC X(7) VALUE '0WHD41Z'.
               10  FILLER           PIC X(7) VALUE '0WHF01Z'.
               10  FILLER           PIC X(7) VALUE '0WHF31Z'.
               10  FILLER           PIC X(7) VALUE '0WHF41Z'.
               10  FILLER           PIC X(7) VALUE '0WHG01Z'.
               10  FILLER           PIC X(7) VALUE '0WHG31Z'.
               10  FILLER           PIC X(7) VALUE '0WHG41Z'.
               10  FILLER           PIC X(7) VALUE '0WHH01Z'.
               10  FILLER           PIC X(7) VALUE '0WHH31Z'.
               10  FILLER           PIC X(7) VALUE '0WHH41Z'.
               10  FILLER           PIC X(7) VALUE '0WHJ01Z'.
               10  FILLER           PIC X(7) VALUE '0WHJ31Z'.
               10  FILLER           PIC X(7) VALUE '0WHJ41Z'.
               10  FILLER           PIC X(7) VALUE '0WHK01Z'.
               10  FILLER           PIC X(7) VALUE '0WHK31Z'.
               10  FILLER           PIC X(7) VALUE '0WHK41Z'.
               10  FILLER           PIC X(7) VALUE '0WHL01Z'.
               10  FILLER           PIC X(7) VALUE '0WHL31Z'.
               10  FILLER           PIC X(7) VALUE '0WHL41Z'.
               10  FILLER           PIC X(7) VALUE '0WHM01Z'.
               10  FILLER           PIC X(7) VALUE '0WHM31Z'.
               10  FILLER           PIC X(7) VALUE '0WHM41Z'.
               10  FILLER           PIC X(7) VALUE '0WHN01Z'.
               10  FILLER           PIC X(7) VALUE '0WHN31Z'.
               10  FILLER           PIC X(7) VALUE '0WHN41Z'.
               10  FILLER           PIC X(7) VALUE '0WHP01Z'.
               10  FILLER           PIC X(7) VALUE '0WHP31Z'.
               10  FILLER           PIC X(7) VALUE '0WHP41Z'.
               10  FILLER           PIC X(7) VALUE '0WHP71Z'.
               10  FILLER           PIC X(7) VALUE '0WHP81Z'.
               10  FILLER           PIC X(7) VALUE '0WHQ01Z'.
               10  FILLER           PIC X(7) VALUE '0WHQ31Z'.
               10  FILLER           PIC X(7) VALUE '0WHQ41Z'.
               10  FILLER           PIC X(7) VALUE '0WHQ71Z'.
               10  FILLER           PIC X(7) VALUE '0WHQ81Z'.
               10  FILLER           PIC X(7) VALUE '0WHR01Z'.
               10  FILLER           PIC X(7) VALUE '0WHR31Z'.
               10  FILLER           PIC X(7) VALUE '0WHR41Z'.
               10  FILLER           PIC X(7) VALUE '0WHR71Z'.
               10  FILLER           PIC X(7) VALUE '0WHR81Z'.
               10  FILLER           PIC X(7) VALUE '0WH001Z'.
               10  FILLER           PIC X(7) VALUE '0WH031Z'.
               10  FILLER           PIC X(7) VALUE '0WH041Z'.
               10  FILLER           PIC X(7) VALUE '0WH101Z'.
               10  FILLER           PIC X(7) VALUE '0WH131Z'.
               10  FILLER           PIC X(7) VALUE '0WH141Z'.
               10  FILLER           PIC X(7) VALUE '0WH201Z'.
               10  FILLER           PIC X(7) VALUE '0WH231Z'.
               10  FILLER           PIC X(7) VALUE '0WH241Z'.
               10  FILLER           PIC X(7) VALUE '0WH301Z'.
               10  FILLER           PIC X(7) VALUE '0WH331Z'.
               10  FILLER           PIC X(7) VALUE '0WH341Z'.
               10  FILLER           PIC X(7) VALUE '0WH401Z'.
               10  FILLER           PIC X(7) VALUE '0WH431Z'.
               10  FILLER           PIC X(7) VALUE '0WH441Z'.
               10  FILLER           PIC X(7) VALUE '0WH501Z'.
               10  FILLER           PIC X(7) VALUE '0WH531Z'.
               10  FILLER           PIC X(7) VALUE '0WH541Z'.
               10  FILLER           PIC X(7) VALUE '0WH601Z'.
               10  FILLER           PIC X(7) VALUE '0WH631Z'.
               10  FILLER           PIC X(7) VALUE '0WH641Z'.
               10  FILLER           PIC X(7) VALUE '0WH801Z'.
               10  FILLER           PIC X(7) VALUE '0WH831Z'.
               10  FILLER           PIC X(7) VALUE '0WH841Z'.
               10  FILLER           PIC X(7) VALUE '0WH901Z'.
               10  FILLER           PIC X(7) VALUE '0WH931Z'.
               10  FILLER           PIC X(7) VALUE '0WH941Z'.
               10  FILLER           PIC X(7) VALUE '0XHB01Z'.
               10  FILLER           PIC X(7) VALUE '0XHB31Z'.
               10  FILLER           PIC X(7) VALUE '0XHB41Z'.
               10  FILLER           PIC X(7) VALUE '0XHC01Z'.
               10  FILLER           PIC X(7) VALUE '0XHC31Z'.
               10  FILLER           PIC X(7) VALUE '0XHC41Z'.
               10  FILLER           PIC X(7) VALUE '0XHD01Z'.
               10  FILLER           PIC X(7) VALUE '0XHD31Z'.
               10  FILLER           PIC X(7) VALUE '0XHD41Z'.
               10  FILLER           PIC X(7) VALUE '0XHF01Z'.
               10  FILLER           PIC X(7) VALUE '0XHF31Z'.
               10  FILLER           PIC X(7) VALUE '0XHF41Z'.
               10  FILLER           PIC X(7) VALUE '0XHG01Z'.
               10  FILLER           PIC X(7) VALUE '0XHG31Z'.
               10  FILLER           PIC X(7) VALUE '0XHG41Z'.
               10  FILLER           PIC X(7) VALUE '0XHH01Z'.
               10  FILLER           PIC X(7) VALUE '0XHH31Z'.
               10  FILLER           PIC X(7) VALUE '0XHH41Z'.
               10  FILLER           PIC X(7) VALUE '0XHJ01Z'.
               10  FILLER           PIC X(7) VALUE '0XHJ31Z'.
               10  FILLER           PIC X(7) VALUE '0XHJ41Z'.
               10  FILLER           PIC X(7) VALUE '0XHK01Z'.
               10  FILLER           PIC X(7) VALUE '0XHK31Z'.
               10  FILLER           PIC X(7) VALUE '0XHK41Z'.
               10  FILLER           PIC X(7) VALUE '0XH201Z'.
               10  FILLER           PIC X(7) VALUE '0XH231Z'.
               10  FILLER           PIC X(7) VALUE '0XH241Z'.
               10  FILLER           PIC X(7) VALUE '0XH301Z'.
               10  FILLER           PIC X(7) VALUE '0XH331Z'.
               10  FILLER           PIC X(7) VALUE '0XH341Z'.
               10  FILLER           PIC X(7) VALUE '0XH401Z'.
               10  FILLER           PIC X(7) VALUE '0XH431Z'.
               10  FILLER           PIC X(7) VALUE '0XH441Z'.
               10  FILLER           PIC X(7) VALUE '0XH501Z'.
               10  FILLER           PIC X(7) VALUE '0XH531Z'.
               10  FILLER           PIC X(7) VALUE '0XH541Z'.
               10  FILLER           PIC X(7) VALUE '0XH601Z'.
               10  FILLER           PIC X(7) VALUE '0XH631Z'.
               10  FILLER           PIC X(7) VALUE '0XH641Z'.
               10  FILLER           PIC X(7) VALUE '0XH701Z'.
               10  FILLER           PIC X(7) VALUE '0XH731Z'.
               10  FILLER           PIC X(7) VALUE '0XH741Z'.
               10  FILLER           PIC X(7) VALUE '0XH801Z'.
               10  FILLER           PIC X(7) VALUE '0XH831Z'.
               10  FILLER           PIC X(7) VALUE '0XH841Z'.
               10  FILLER           PIC X(7) VALUE '0XH901Z'.
               10  FILLER           PIC X(7) VALUE '0XH931Z'.
               10  FILLER           PIC X(7) VALUE '0XH941Z'.
               10  FILLER           PIC X(7) VALUE '0YHB01Z'.
               10  FILLER           PIC X(7) VALUE '0YHB31Z'.
               10  FILLER           PIC X(7) VALUE '0YHB41Z'.
               10  FILLER           PIC X(7) VALUE '0YHC01Z'.
               10  FILLER           PIC X(7) VALUE '0YHC31Z'.
               10  FILLER           PIC X(7) VALUE '0YHC41Z'.
               10  FILLER           PIC X(7) VALUE '0YHD01Z'.
               10  FILLER           PIC X(7) VALUE '0YHD31Z'.
               10  FILLER           PIC X(7) VALUE '0YHD41Z'.
               10  FILLER           PIC X(7) VALUE '0YHF01Z'.
               10  FILLER           PIC X(7) VALUE '0YHF31Z'.
               10  FILLER           PIC X(7) VALUE '0YHF41Z'.
               10  FILLER           PIC X(7) VALUE '0YHG01Z'.
               10  FILLER           PIC X(7) VALUE '0YHG31Z'.
               10  FILLER           PIC X(7) VALUE '0YHG41Z'.
               10  FILLER           PIC X(7) VALUE '0YHH01Z'.
               10  FILLER           PIC X(7) VALUE '0YHH31Z'.
               10  FILLER           PIC X(7) VALUE '0YHH41Z'.
               10  FILLER           PIC X(7) VALUE '0YHJ01Z'.
               10  FILLER           PIC X(7) VALUE '0YHJ31Z'.
               10  FILLER           PIC X(7) VALUE '0YHJ41Z'.
               10  FILLER           PIC X(7) VALUE '0YHK01Z'.
               10  FILLER           PIC X(7) VALUE '0YHK31Z'.
               10  FILLER           PIC X(7) VALUE '0YHK41Z'.
               10  FILLER           PIC X(7) VALUE '0YHL01Z'.
               10  FILLER           PIC X(7) VALUE '0YHL31Z'.
               10  FILLER           PIC X(7) VALUE '0YHL41Z'.
               10  FILLER           PIC X(7) VALUE '0YHM01Z'.
               10  FILLER           PIC X(7) VALUE '0YHM31Z'.
               10  FILLER           PIC X(7) VALUE '0YHM41Z'.
               10  FILLER           PIC X(7) VALUE '0YHN01Z'.
               10  FILLER           PIC X(7) VALUE '0YHN31Z'.
               10  FILLER           PIC X(7) VALUE '0YHN41Z'.
               10  FILLER           PIC X(7) VALUE '0YH001Z'.
               10  FILLER           PIC X(7) VALUE '0YH031Z'.
               10  FILLER           PIC X(7) VALUE '0YH041Z'.
               10  FILLER           PIC X(7) VALUE '0YH101Z'.
               10  FILLER           PIC X(7) VALUE '0YH131Z'.
               10  FILLER           PIC X(7) VALUE '0YH141Z'.
               10  FILLER           PIC X(7) VALUE '0YH501Z'.
               10  FILLER           PIC X(7) VALUE '0YH531Z'.
               10  FILLER           PIC X(7) VALUE '0YH541Z'.
               10  FILLER           PIC X(7) VALUE '0YH601Z'.
               10  FILLER           PIC X(7) VALUE '0YH631Z'.
               10  FILLER           PIC X(7) VALUE '0YH641Z'.
               10  FILLER           PIC X(7) VALUE '0YH701Z'.
               10  FILLER           PIC X(7) VALUE '0YH731Z'.
               10  FILLER           PIC X(7) VALUE '0YH741Z'.
               10  FILLER           PIC X(7) VALUE '0YH801Z'.
               10  FILLER           PIC X(7) VALUE '0YH831Z'.
               10  FILLER           PIC X(7) VALUE '0YH841Z'.
               10  FILLER           PIC X(7) VALUE '0YH901Z'.
               10  FILLER           PIC X(7) VALUE '0YH931Z'.
               10  FILLER           PIC X(7) VALUE '0YH941Z'.
               10  FILLER           PIC X(7) VALUE '00HE01Z'.
               10  FILLER           PIC X(7) VALUE '00HE31Z'.
               10  FILLER           PIC X(7) VALUE '00HE41Z'.
               10  FILLER           PIC X(7) VALUE '00HU01Z'.
               10  FILLER           PIC X(7) VALUE '00HU31Z'.
               10  FILLER           PIC X(7) VALUE '00HU41Z'.
               10  FILLER           PIC X(7) VALUE '00HV01Z'.
               10  FILLER           PIC X(7) VALUE '00HV31Z'.
               10  FILLER           PIC X(7) VALUE '00HV41Z'.
               10  FILLER           PIC X(7) VALUE '00H001Z'.
               10  FILLER           PIC X(7) VALUE '00H004Z'.
               10  FILLER           PIC X(7) VALUE '00H031Z'.
               10  FILLER           PIC X(7) VALUE '00H041Z'.
               10  FILLER           PIC X(7) VALUE '00H601Z'.
               10  FILLER           PIC X(7) VALUE '00H631Z'.
               10  FILLER           PIC X(7) VALUE '00H641Z'.
               10  FILLER           PIC X(7) VALUE '01HY01Z'.
               10  FILLER           PIC X(7) VALUE '01HY31Z'.
               10  FILLER           PIC X(7) VALUE '01HY41Z'.
               10  FILLER           PIC X(7) VALUE '07HK01Z'.
               10  FILLER           PIC X(7) VALUE '07HK31Z'.
               10  FILLER           PIC X(7) VALUE '07HK41Z'.
               10  FILLER           PIC X(7) VALUE '07HL01Z'.
               10  FILLER           PIC X(7) VALUE '07HL31Z'.
               10  FILLER           PIC X(7) VALUE '07HL41Z'.
               10  FILLER           PIC X(7) VALUE '07HM01Z'.
               10  FILLER           PIC X(7) VALUE '07HM31Z'.
               10  FILLER           PIC X(7) VALUE '07HM41Z'.
               10  FILLER           PIC X(7) VALUE '07HN01Z'.
               10  FILLER           PIC X(7) VALUE '07HN31Z'.
               10  FILLER           PIC X(7) VALUE '07HN41Z'.
               10  FILLER           PIC X(7) VALUE '07HP01Z'.
               10  FILLER           PIC X(7) VALUE '07HP31Z'.
               10  FILLER           PIC X(7) VALUE '07HP41Z'.
               10  FILLER           PIC X(7) VALUE '07HT01Z'.
               10  FILLER           PIC X(7) VALUE '07HT31Z'.
               10  FILLER           PIC X(7) VALUE '07HT41Z'.
               10  FILLER           PIC X(7) VALUE '08H0X1Z'.
               10  FILLER           PIC X(7) VALUE '08H031Z'.
               10  FILLER           PIC X(7) VALUE '08H1X1Z'.
               10  FILLER           PIC X(7) VALUE '08H131Z'.
               10  FILLER           PIC X(7) VALUE '09HD01Z'.
               10  FILLER           PIC X(7) VALUE '09HD31Z'.
               10  FILLER           PIC X(7) VALUE '09HD41Z'.
               10  FILLER           PIC X(7) VALUE '09HE01Z'.
               10  FILLER           PIC X(7) VALUE '09HE31Z'.
               10  FILLER           PIC X(7) VALUE '09HE41Z'.
               10  FILLER           PIC X(7) VALUE '09HH01Z'.
               10  FILLER           PIC X(7) VALUE '09HH31Z'.
               10  FILLER           PIC X(7) VALUE '09HH41Z'.
               10  FILLER           PIC X(7) VALUE '09HH71Z'.
               10  FILLER           PIC X(7) VALUE '09HH81Z'.
               10  FILLER           PIC X(7) VALUE '09HJ01Z'.
               10  FILLER           PIC X(7) VALUE '09HJ31Z'.
               10  FILLER           PIC X(7) VALUE '09HJ41Z'.
               10  FILLER           PIC X(7) VALUE '09HJ71Z'.
               10  FILLER           PIC X(7) VALUE '09HJ81Z'.
               10  FILLER           PIC X(7) VALUE '09HK01Z'.
               10  FILLER           PIC X(7) VALUE '09HK31Z'.
               10  FILLER           PIC X(7) VALUE '09HK41Z'.
               10  FILLER           PIC X(7) VALUE '09HK71Z'.
               10  FILLER           PIC X(7) VALUE '09HK81Z'.
               10  FILLER           PIC X(7) VALUE '09HN71Z'.
               10  FILLER           PIC X(7) VALUE '09HN81Z'.
               10  FILLER           PIC X(7) VALUE '09HY01Z'.
               10  FILLER           PIC X(7) VALUE '09HY31Z'.
               10  FILLER           PIC X(7) VALUE '09HY41Z'.
               10  FILLER           PIC X(7) VALUE '09HY71Z'.
               10  FILLER           PIC X(7) VALUE '09HY81Z'.
               10  FILLER           PIC X(7) VALUE '3E0A305'.
               10  FILLER           PIC X(7) VALUE '3E0BX05'.
               10  FILLER           PIC X(7) VALUE '3E0B305'.
               10  FILLER           PIC X(7) VALUE '3E0B705'.
               10  FILLER           PIC X(7) VALUE '3E0CX05'.
               10  FILLER           PIC X(7) VALUE '3E0C305'.
               10  FILLER           PIC X(7) VALUE '3E0C705'.
               10  FILLER           PIC X(7) VALUE '3E0DX05'.
               10  FILLER           PIC X(7) VALUE '3E0D305'.
               10  FILLER           PIC X(7) VALUE '3E0D705'.
               10  FILLER           PIC X(7) VALUE '3E0E305'.
               10  FILLER           PIC X(7) VALUE '3E0E705'.
               10  FILLER           PIC X(7) VALUE '3E0E805'.
               10  FILLER           PIC X(7) VALUE '3E0F305'.
               10  FILLER           PIC X(7) VALUE '3E0F705'.
               10  FILLER           PIC X(7) VALUE '3E0F805'.
               10  FILLER           PIC X(7) VALUE '3E0G305'.
               10  FILLER           PIC X(7) VALUE '3E0G705'.
               10  FILLER           PIC X(7) VALUE '3E0G805'.
               10  FILLER           PIC X(7) VALUE '3E0H305'.
               10  FILLER           PIC X(7) VALUE '3E0H705'.
               10  FILLER           PIC X(7) VALUE '3E0H805'.
               10  FILLER           PIC X(7) VALUE '3E0J305'.
               10  FILLER           PIC X(7) VALUE '3E0J705'.
               10  FILLER           PIC X(7) VALUE '3E0J805'.
               10  FILLER           PIC X(7) VALUE '3E0K305'.
               10  FILLER           PIC X(7) VALUE '3E0K705'.
               10  FILLER           PIC X(7) VALUE '3E0K805'.
               10  FILLER           PIC X(7) VALUE '3E0L305'.
               10  FILLER           PIC X(7) VALUE '3E0L705'.
               10  FILLER           PIC X(7) VALUE '3E0M305'.
               10  FILLER           PIC X(7) VALUE '3E0M705'.
               10  FILLER           PIC X(7) VALUE '3E0N305'.
               10  FILLER           PIC X(7) VALUE '3E0N705'.
               10  FILLER           PIC X(7) VALUE '3E0N805'.
               10  FILLER           PIC X(7) VALUE '3E0P305'.
               10  FILLER           PIC X(7) VALUE '3E0P705'.
               10  FILLER           PIC X(7) VALUE '3E0P805'.
               10  FILLER           PIC X(7) VALUE '3E0Q005'.
               10  FILLER           PIC X(7) VALUE '3E0Q305'.
               10  FILLER           PIC X(7) VALUE '3E0Q705'.
               10  FILLER           PIC X(7) VALUE '3E0R305'.
               10  FILLER           PIC X(7) VALUE '3E0S305'.
               10  FILLER           PIC X(7) VALUE '3E0U305'.
               10  FILLER           PIC X(7) VALUE '3E0V305'.
               10  FILLER           PIC X(7) VALUE '3E0W305'.
               10  FILLER           PIC X(7) VALUE '3E0Y305'.
               10  FILLER           PIC X(7) VALUE '3E0Y705'.
               10  FILLER           PIC X(7) VALUE '3E00X05'.
               10  FILLER           PIC X(7) VALUE '3E01305'.
               10  FILLER           PIC X(7) VALUE '3E02305'.
               10  FILLER           PIC X(7) VALUE '3E03005'.
               10  FILLER           PIC X(7) VALUE '3E03305'.
               10  FILLER           PIC X(7) VALUE '3E04005'.
               10  FILLER           PIC X(7) VALUE '3E04305'.
               10  FILLER           PIC X(7) VALUE '3E05005'.
               10  FILLER           PIC X(7) VALUE '3E05305'.
               10  FILLER           PIC X(7) VALUE '3E06005'.
               10  FILLER           PIC X(7) VALUE '3E06305'.
               10  FILLER           PIC X(7) VALUE '3E09X05'.
               10  FILLER           PIC X(7) VALUE '3E09305'.
               10  FILLER           PIC X(7) VALUE '3E09705'.
           05  FILLER     REDEFINES CAT6P-TABLE-DATA.
               10  CAT6P-DATA       OCCURS 2403 TIMES
                                    ASCENDING KEY IS CAT6P-CODE
                                    INDEXED BY IX-CAT6P.
                   15  CAT6P-CODE   PIC X(7).

      ****************************************************************
      * UNCONTROLLED DIABETES - MELLITUS W OR WO COMPLICATIONS       *
      ****************************************************************
       01 CAT7-TABLE.
           05  CAT7-TABLE-DATA.
               10  FILLER           PIC X(7) VALUE 'E1065  '.
               10  FILLER           PIC X(7) VALUE 'E1165  '.
           05  FILLER     REDEFINES CAT7-TABLE-DATA.
               10  CAT7-DATA        OCCURS 2 TIMES
                                    ASCENDING KEY IS CAT7-CODE
                                    INDEXED BY IX-CAT7.
                   15  CAT7-CODE    PIC X(7).

      ****************************************************************
      * SEVERE PROTEIN CALORIE MALNUTRITION                          *
      ****************************************************************
       01 CAT8-TABLE.
           05  CAT8-TABLE-DATA.
               10  FILLER           PIC X(7) VALUE 'E40    '.
               10  FILLER           PIC X(7) VALUE 'E41    '.
               10  FILLER           PIC X(7) VALUE 'E42    '.
               10  FILLER           PIC X(7) VALUE 'E43    '.
           05  FILLER     REDEFINES CAT8-TABLE-DATA.
               10  CAT8-DATA        OCCURS  4 TIMES
                                    ASCENDING KEY IS CAT8-CODE
                                    INDEXED BY IX-CAT8.
                   15  CAT8-CODE    PIC X(7).

      ****************************************************************
      * EATING AND CONDUCT DISORDERS                                 *
      ****************************************************************
       01 CAT9-TABLE.
           05  CAT9-TABLE-DATA.
               10  FILLER           PIC X(7) VALUE 'F5000  '.
               10  FILLER           PIC X(7) VALUE 'F5001  '.
               10  FILLER           PIC X(7) VALUE 'F5002  '.
               10  FILLER           PIC X(7) VALUE 'F509   '.
               10  FILLER           PIC X(7) VALUE 'F631   '.
               10  FILLER           PIC X(7) VALUE 'F6381  '.
               10  FILLER           PIC X(7) VALUE 'F911   '.
           05  FILLER     REDEFINES CAT9-TABLE-DATA.
               10  CAT9-DATA        OCCURS  7 TIMES
                                    ASCENDING KEY IS CAT9-CODE
                                    INDEXED BY IX-CAT9.
                   15  CAT9-CODE    PIC X(7).

      ****************************************************************
      * INFECTIOUS DISEASE                                           *
      ****************************************************************
       01 CAT10-TABLE.
           05  CAT10-TABLE-DATA.
               10  FILLER           PIC X(7) VALUE 'A150   '.
               10  FILLER           PIC X(7) VALUE 'A154   '.
               10  FILLER           PIC X(7) VALUE 'A155   '.
               10  FILLER           PIC X(7) VALUE 'A156   '.
               10  FILLER           PIC X(7) VALUE 'A157   '.
               10  FILLER           PIC X(7) VALUE 'A158   '.
               10  FILLER           PIC X(7) VALUE 'A159   '.
               10  FILLER           PIC X(7) VALUE 'A170   '.
               10  FILLER           PIC X(7) VALUE 'A171   '.
               10  FILLER           PIC X(7) VALUE 'A1781  '.
               10  FILLER           PIC X(7) VALUE 'A1782  '.
               10  FILLER           PIC X(7) VALUE 'A1783  '.
               10  FILLER           PIC X(7) VALUE 'A1789  '.
               10  FILLER           PIC X(7) VALUE 'A179   '.
               10  FILLER           PIC X(7) VALUE 'A1801  '.
               10  FILLER           PIC X(7) VALUE 'A1802  '.
               10  FILLER           PIC X(7) VALUE 'A1803  '.
               10  FILLER           PIC X(7) VALUE 'A1809  '.
               10  FILLER           PIC X(7) VALUE 'A1810  '.
               10  FILLER           PIC X(7) VALUE 'A1811  '.
               10  FILLER           PIC X(7) VALUE 'A1812  '.
               10  FILLER           PIC X(7) VALUE 'A1813  '.
               10  FILLER           PIC X(7) VALUE 'A1814  '.
               10  FILLER           PIC X(7) VALUE 'A1815  '.
               10  FILLER           PIC X(7) VALUE 'A1816  '.
               10  FILLER           PIC X(7) VALUE 'A1817  '.
               10  FILLER           PIC X(7) VALUE 'A1818  '.
               10  FILLER           PIC X(7) VALUE 'A182   '.
               10  FILLER           PIC X(7) VALUE 'A1831  '.
               10  FILLER           PIC X(7) VALUE 'A1832  '.
               10  FILLER           PIC X(7) VALUE 'A1839  '.
               10  FILLER           PIC X(7) VALUE 'A184   '.
               10  FILLER           PIC X(7) VALUE 'A1850  '.
               10  FILLER           PIC X(7) VALUE 'A1851  '.
               10  FILLER           PIC X(7) VALUE 'A1852  '.
               10  FILLER           PIC X(7) VALUE 'A1853  '.
               10  FILLER           PIC X(7) VALUE 'A1854  '.
               10  FILLER           PIC X(7) VALUE 'A1859  '.
               10  FILLER           PIC X(7) VALUE 'A186   '.
               10  FILLER           PIC X(7) VALUE 'A187   '.
               10  FILLER           PIC X(7) VALUE 'A1881  '.
               10  FILLER           PIC X(7) VALUE 'A1882  '.
               10  FILLER           PIC X(7) VALUE 'A1883  '.
               10  FILLER           PIC X(7) VALUE 'A1884  '.
               10  FILLER           PIC X(7) VALUE 'A1885  '.
               10  FILLER           PIC X(7) VALUE 'A1889  '.
               10  FILLER           PIC X(7) VALUE 'A190   '.
               10  FILLER           PIC X(7) VALUE 'A191   '.
               10  FILLER           PIC X(7) VALUE 'A192   '.
               10  FILLER           PIC X(7) VALUE 'A198   '.
               10  FILLER           PIC X(7) VALUE 'A199   '.
               10  FILLER           PIC X(7) VALUE 'A200   '.
               10  FILLER           PIC X(7) VALUE 'A201   '.
               10  FILLER           PIC X(7) VALUE 'A202   '.
               10  FILLER           PIC X(7) VALUE 'A203   '.
               10  FILLER           PIC X(7) VALUE 'A207   '.
               10  FILLER           PIC X(7) VALUE 'A208   '.
               10  FILLER           PIC X(7) VALUE 'A209   '.
               10  FILLER           PIC X(7) VALUE 'A210   '.
               10  FILLER           PIC X(7) VALUE 'A211   '.
               10  FILLER           PIC X(7) VALUE 'A212   '.
               10  FILLER           PIC X(7) VALUE 'A213   '.
               10  FILLER           PIC X(7) VALUE 'A217   '.
               10  FILLER           PIC X(7) VALUE 'A218   '.
               10  FILLER           PIC X(7) VALUE 'A219   '.
               10  FILLER           PIC X(7) VALUE 'A220   '.
               10  FILLER           PIC X(7) VALUE 'A221   '.
               10  FILLER           PIC X(7) VALUE 'A222   '.
               10  FILLER           PIC X(7) VALUE 'A227   '.
               10  FILLER           PIC X(7) VALUE 'A228   '.
               10  FILLER           PIC X(7) VALUE 'A229   '.
               10  FILLER           PIC X(7) VALUE 'A230   '.
               10  FILLER           PIC X(7) VALUE 'A231   '.
               10  FILLER           PIC X(7) VALUE 'A232   '.
               10  FILLER           PIC X(7) VALUE 'A233   '.
               10  FILLER           PIC X(7) VALUE 'A238   '.
               10  FILLER           PIC X(7) VALUE 'A239   '.
               10  FILLER           PIC X(7) VALUE 'A240   '.
               10  FILLER           PIC X(7) VALUE 'A241   '.
               10  FILLER           PIC X(7) VALUE 'A242   '.
               10  FILLER           PIC X(7) VALUE 'A243   '.
               10  FILLER           PIC X(7) VALUE 'A249   '.
               10  FILLER           PIC X(7) VALUE 'A250   '.
               10  FILLER           PIC X(7) VALUE 'A251   '.
               10  FILLER           PIC X(7) VALUE 'A259   '.
               10  FILLER           PIC X(7) VALUE 'A260   '.
               10  FILLER           PIC X(7) VALUE 'A267   '.
               10  FILLER           PIC X(7) VALUE 'A268   '.
               10  FILLER           PIC X(7) VALUE 'A269   '.
               10  FILLER           PIC X(7) VALUE 'A280   '.
               10  FILLER           PIC X(7) VALUE 'A281   '.
               10  FILLER           PIC X(7) VALUE 'A282   '.
               10  FILLER           PIC X(7) VALUE 'A288   '.
               10  FILLER           PIC X(7) VALUE 'A289   '.
               10  FILLER           PIC X(7) VALUE 'A300   '.
               10  FILLER           PIC X(7) VALUE 'A301   '.
               10  FILLER           PIC X(7) VALUE 'A302   '.
               10  FILLER           PIC X(7) VALUE 'A303   '.
               10  FILLER           PIC X(7) VALUE 'A304   '.
               10  FILLER           PIC X(7) VALUE 'A305   '.
               10  FILLER           PIC X(7) VALUE 'A308   '.
               10  FILLER           PIC X(7) VALUE 'A309   '.
               10  FILLER           PIC X(7) VALUE 'A310   '.
               10  FILLER           PIC X(7) VALUE 'A311   '.
               10  FILLER           PIC X(7) VALUE 'A312   '.
               10  FILLER           PIC X(7) VALUE 'A318   '.
               10  FILLER           PIC X(7) VALUE 'A319   '.
               10  FILLER           PIC X(7) VALUE 'A320   '.
               10  FILLER           PIC X(7) VALUE 'A3211  '.
               10  FILLER           PIC X(7) VALUE 'A3212  '.
               10  FILLER           PIC X(7) VALUE 'A327   '.
               10  FILLER           PIC X(7) VALUE 'A3281  '.
               10  FILLER           PIC X(7) VALUE 'A3282  '.
               10  FILLER           PIC X(7) VALUE 'A3289  '.
               10  FILLER           PIC X(7) VALUE 'A329   '.
               10  FILLER           PIC X(7) VALUE 'A35    '.
               10  FILLER           PIC X(7) VALUE 'A360   '.
               10  FILLER           PIC X(7) VALUE 'A361   '.
               10  FILLER           PIC X(7) VALUE 'A362   '.
               10  FILLER           PIC X(7) VALUE 'A363   '.
               10  FILLER           PIC X(7) VALUE 'A3681  '.
               10  FILLER           PIC X(7) VALUE 'A3682  '.
               10  FILLER           PIC X(7) VALUE 'A3683  '.
               10  FILLER           PIC X(7) VALUE 'A3684  '.
               10  FILLER           PIC X(7) VALUE 'A3685  '.
               10  FILLER           PIC X(7) VALUE 'A3686  '.
               10  FILLER           PIC X(7) VALUE 'A3689  '.
               10  FILLER           PIC X(7) VALUE 'A369   '.
               10  FILLER           PIC X(7) VALUE 'A3700  '.
               10  FILLER           PIC X(7) VALUE 'A3701  '.
               10  FILLER           PIC X(7) VALUE 'A3710  '.
               10  FILLER           PIC X(7) VALUE 'A3711  '.
               10  FILLER           PIC X(7) VALUE 'A3780  '.
               10  FILLER           PIC X(7) VALUE 'A3781  '.
               10  FILLER           PIC X(7) VALUE 'A3790  '.
               10  FILLER           PIC X(7) VALUE 'A3791  '.
               10  FILLER           PIC X(7) VALUE 'A380   '.
               10  FILLER           PIC X(7) VALUE 'A381   '.
               10  FILLER           PIC X(7) VALUE 'A388   '.
               10  FILLER           PIC X(7) VALUE 'A389   '.
               10  FILLER           PIC X(7) VALUE 'A390   '.
               10  FILLER           PIC X(7) VALUE 'A391   '.
               10  FILLER           PIC X(7) VALUE 'A392   '.
               10  FILLER           PIC X(7) VALUE 'A393   '.
               10  FILLER           PIC X(7) VALUE 'A394   '.
               10  FILLER           PIC X(7) VALUE 'A3950  '.
               10  FILLER           PIC X(7) VALUE 'A3951  '.
               10  FILLER           PIC X(7) VALUE 'A3952  '.
               10  FILLER           PIC X(7) VALUE 'A3953  '.
               10  FILLER           PIC X(7) VALUE 'A3981  '.
               10  FILLER           PIC X(7) VALUE 'A3982  '.
               10  FILLER           PIC X(7) VALUE 'A3983  '.
               10  FILLER           PIC X(7) VALUE 'A3984  '.
               10  FILLER           PIC X(7) VALUE 'A3989  '.
               10  FILLER           PIC X(7) VALUE 'A399   '.
               10  FILLER           PIC X(7) VALUE 'A400   '.
               10  FILLER           PIC X(7) VALUE 'A401   '.
               10  FILLER           PIC X(7) VALUE 'A403   '.
               10  FILLER           PIC X(7) VALUE 'A408   '.
               10  FILLER           PIC X(7) VALUE 'A409   '.
               10  FILLER           PIC X(7) VALUE 'A4101  '.
               10  FILLER           PIC X(7) VALUE 'A4102  '.
               10  FILLER           PIC X(7) VALUE 'A411   '.
               10  FILLER           PIC X(7) VALUE 'A412   '.
               10  FILLER           PIC X(7) VALUE 'A413   '.
               10  FILLER           PIC X(7) VALUE 'A414   '.
               10  FILLER           PIC X(7) VALUE 'A4150  '.
               10  FILLER           PIC X(7) VALUE 'A4151  '.
               10  FILLER           PIC X(7) VALUE 'A4152  '.
               10  FILLER           PIC X(7) VALUE 'A4153  '.
               10  FILLER           PIC X(7) VALUE 'A4159  '.
               10  FILLER           PIC X(7) VALUE 'A4181  '.
               10  FILLER           PIC X(7) VALUE 'A4189  '.
               10  FILLER           PIC X(7) VALUE 'A419   '.
               10  FILLER           PIC X(7) VALUE 'A420   '.
               10  FILLER           PIC X(7) VALUE 'A421   '.
               10  FILLER           PIC X(7) VALUE 'A422   '.
               10  FILLER           PIC X(7) VALUE 'A427   '.
               10  FILLER           PIC X(7) VALUE 'A4281  '.
               10  FILLER           PIC X(7) VALUE 'A4282  '.
               10  FILLER           PIC X(7) VALUE 'A4289  '.
               10  FILLER           PIC X(7) VALUE 'A429   '.
               10  FILLER           PIC X(7) VALUE 'A430   '.
               10  FILLER           PIC X(7) VALUE 'A431   '.
               10  FILLER           PIC X(7) VALUE 'A438   '.
               10  FILLER           PIC X(7) VALUE 'A439   '.
               10  FILLER           PIC X(7) VALUE 'A46    '.
               10  FILLER           PIC X(7) VALUE 'A480   '.
               10  FILLER           PIC X(7) VALUE 'A482   '.
               10  FILLER           PIC X(7) VALUE 'A483   '.
               10  FILLER           PIC X(7) VALUE 'A484   '.
               10  FILLER           PIC X(7) VALUE 'A4851  '.
               10  FILLER           PIC X(7) VALUE 'A4852  '.
               10  FILLER           PIC X(7) VALUE 'A488   '.
               10  FILLER           PIC X(7) VALUE 'A491   '.
               10  FILLER           PIC X(7) VALUE 'A70    '.
               10  FILLER           PIC X(7) VALUE 'A710   '.
               10  FILLER           PIC X(7) VALUE 'A711   '.
               10  FILLER           PIC X(7) VALUE 'A719   '.
               10  FILLER           PIC X(7) VALUE 'A740   '.
               10  FILLER           PIC X(7) VALUE 'A7489  '.
               10  FILLER           PIC X(7) VALUE 'A800   '.
               10  FILLER           PIC X(7) VALUE 'A801   '.
               10  FILLER           PIC X(7) VALUE 'A802   '.
               10  FILLER           PIC X(7) VALUE 'A8030  '.
               10  FILLER           PIC X(7) VALUE 'A8039  '.
               10  FILLER           PIC X(7) VALUE 'A804   '.
               10  FILLER           PIC X(7) VALUE 'A809   '.
               10  FILLER           PIC X(7) VALUE 'A8100  '.
               10  FILLER           PIC X(7) VALUE 'A8101  '.
               10  FILLER           PIC X(7) VALUE 'A8109  '.
               10  FILLER           PIC X(7) VALUE 'A811   '.
               10  FILLER           PIC X(7) VALUE 'A812   '.
               10  FILLER           PIC X(7) VALUE 'A8181  '.
               10  FILLER           PIC X(7) VALUE 'A8182  '.
               10  FILLER           PIC X(7) VALUE 'A8183  '.
               10  FILLER           PIC X(7) VALUE 'A8189  '.
               10  FILLER           PIC X(7) VALUE 'A819   '.
               10  FILLER           PIC X(7) VALUE 'A820   '.
               10  FILLER           PIC X(7) VALUE 'A821   '.
               10  FILLER           PIC X(7) VALUE 'A829   '.
               10  FILLER           PIC X(7) VALUE 'A830   '.
               10  FILLER           PIC X(7) VALUE 'A831   '.
               10  FILLER           PIC X(7) VALUE 'A832   '.
               10  FILLER           PIC X(7) VALUE 'A833   '.
               10  FILLER           PIC X(7) VALUE 'A834   '.
               10  FILLER           PIC X(7) VALUE 'A835   '.
               10  FILLER           PIC X(7) VALUE 'A836   '.
               10  FILLER           PIC X(7) VALUE 'A838   '.
               10  FILLER           PIC X(7) VALUE 'A839   '.
               10  FILLER           PIC X(7) VALUE 'A840   '.
               10  FILLER           PIC X(7) VALUE 'A841   '.
               10  FILLER           PIC X(7) VALUE 'A8481  '.
               10  FILLER           PIC X(7) VALUE 'A8489  '.
               10  FILLER           PIC X(7) VALUE 'A849   '.
               10  FILLER           PIC X(7) VALUE 'A850   '.
               10  FILLER           PIC X(7) VALUE 'A851   '.
               10  FILLER           PIC X(7) VALUE 'A852   '.
               10  FILLER           PIC X(7) VALUE 'A858   '.
               10  FILLER           PIC X(7) VALUE 'A86    '.
               10  FILLER           PIC X(7) VALUE 'A870   '.
               10  FILLER           PIC X(7) VALUE 'A871   '.
               10  FILLER           PIC X(7) VALUE 'A872   '.
               10  FILLER           PIC X(7) VALUE 'A878   '.
               10  FILLER           PIC X(7) VALUE 'A879   '.
               10  FILLER           PIC X(7) VALUE 'A880   '.
               10  FILLER           PIC X(7) VALUE 'A881   '.
               10  FILLER           PIC X(7) VALUE 'A888   '.
               10  FILLER           PIC X(7) VALUE 'A89    '.
               10  FILLER           PIC X(7) VALUE 'A90    '.
               10  FILLER           PIC X(7) VALUE 'A91    '.
               10  FILLER           PIC X(7) VALUE 'A920   '.
               10  FILLER           PIC X(7) VALUE 'A921   '.
               10  FILLER           PIC X(7) VALUE 'A922   '.
               10  FILLER           PIC X(7) VALUE 'A9230  '.
               10  FILLER           PIC X(7) VALUE 'A9231  '.
               10  FILLER           PIC X(7) VALUE 'A9232  '.
               10  FILLER           PIC X(7) VALUE 'A9239  '.
               10  FILLER           PIC X(7) VALUE 'A924   '.
               10  FILLER           PIC X(7) VALUE 'A925   '.
               10  FILLER           PIC X(7) VALUE 'A928   '.
               10  FILLER           PIC X(7) VALUE 'A929   '.
               10  FILLER           PIC X(7) VALUE 'A930   '.
               10  FILLER           PIC X(7) VALUE 'A931   '.
               10  FILLER           PIC X(7) VALUE 'A932   '.
               10  FILLER           PIC X(7) VALUE 'A938   '.
               10  FILLER           PIC X(7) VALUE 'A94    '.
               10  FILLER           PIC X(7) VALUE 'A950   '.
               10  FILLER           PIC X(7) VALUE 'A951   '.
               10  FILLER           PIC X(7) VALUE 'A959   '.
               10  FILLER           PIC X(7) VALUE 'A960   '.
               10  FILLER           PIC X(7) VALUE 'A961   '.
               10  FILLER           PIC X(7) VALUE 'A962   '.
               10  FILLER           PIC X(7) VALUE 'A968   '.
               10  FILLER           PIC X(7) VALUE 'A969   '.
               10  FILLER           PIC X(7) VALUE 'A980   '.
               10  FILLER           PIC X(7) VALUE 'A981   '.
               10  FILLER           PIC X(7) VALUE 'A982   '.
               10  FILLER           PIC X(7) VALUE 'A983   '.
               10  FILLER           PIC X(7) VALUE 'A984   '.
               10  FILLER           PIC X(7) VALUE 'A985   '.
               10  FILLER           PIC X(7) VALUE 'A988   '.
               10  FILLER           PIC X(7) VALUE 'A99    '.
               10  FILLER           PIC X(7) VALUE 'B0050  '.
               10  FILLER           PIC X(7) VALUE 'B0051  '.
               10  FILLER           PIC X(7) VALUE 'B0052  '.
               10  FILLER           PIC X(7) VALUE 'B0053  '.
               10  FILLER           PIC X(7) VALUE 'B0059  '.
               10  FILLER           PIC X(7) VALUE 'B010   '.
               10  FILLER           PIC X(7) VALUE 'B0111  '.
               10  FILLER           PIC X(7) VALUE 'B0112  '.
               10  FILLER           PIC X(7) VALUE 'B012   '.
               10  FILLER           PIC X(7) VALUE 'B0181  '.
               10  FILLER           PIC X(7) VALUE 'B0189  '.
               10  FILLER           PIC X(7) VALUE 'B019   '.
               10  FILLER           PIC X(7) VALUE 'B020   '.
               10  FILLER           PIC X(7) VALUE 'B021   '.
               10  FILLER           PIC X(7) VALUE 'B0221  '.
               10  FILLER           PIC X(7) VALUE 'B0222  '.
               10  FILLER           PIC X(7) VALUE 'B0223  '.
               10  FILLER           PIC X(7) VALUE 'B0224  '.
               10  FILLER           PIC X(7) VALUE 'B0229  '.
               10  FILLER           PIC X(7) VALUE 'B03    '.
               10  FILLER           PIC X(7) VALUE 'B04    '.
               10  FILLER           PIC X(7) VALUE 'B050   '.
               10  FILLER           PIC X(7) VALUE 'B051   '.
               10  FILLER           PIC X(7) VALUE 'B052   '.
               10  FILLER           PIC X(7) VALUE 'B053   '.
               10  FILLER           PIC X(7) VALUE 'B054   '.
               10  FILLER           PIC X(7) VALUE 'B0581  '.
               10  FILLER           PIC X(7) VALUE 'B0589  '.
               10  FILLER           PIC X(7) VALUE 'B059   '.
               10  FILLER           PIC X(7) VALUE 'B0600  '.
               10  FILLER           PIC X(7) VALUE 'B0601  '.
               10  FILLER           PIC X(7) VALUE 'B0602  '.
               10  FILLER           PIC X(7) VALUE 'B0609  '.
               10  FILLER           PIC X(7) VALUE 'B0681  '.
               10  FILLER           PIC X(7) VALUE 'B0682  '.
               10  FILLER           PIC X(7) VALUE 'B0689  '.
               10  FILLER           PIC X(7) VALUE 'B069   '.
               10  FILLER           PIC X(7) VALUE 'B08010 '.
               10  FILLER           PIC X(7) VALUE 'B08011 '.
               10  FILLER           PIC X(7) VALUE 'B0802  '.
               10  FILLER           PIC X(7) VALUE 'B0803  '.
               10  FILLER           PIC X(7) VALUE 'B0804  '.
               10  FILLER           PIC X(7) VALUE 'B0809  '.
               10  FILLER           PIC X(7) VALUE 'B0820  '.
               10  FILLER           PIC X(7) VALUE 'B0821  '.
               10  FILLER           PIC X(7) VALUE 'B0822  '.
               10  FILLER           PIC X(7) VALUE 'B083   '.
               10  FILLER           PIC X(7) VALUE 'B084   '.
               10  FILLER           PIC X(7) VALUE 'B085   '.
               10  FILLER           PIC X(7) VALUE 'B0860  '.
               10  FILLER           PIC X(7) VALUE 'B0861  '.
               10  FILLER           PIC X(7) VALUE 'B0862  '.
               10  FILLER           PIC X(7) VALUE 'B0869  '.
               10  FILLER           PIC X(7) VALUE 'B0870  '.
               10  FILLER           PIC X(7) VALUE 'B0871  '.
               10  FILLER           PIC X(7) VALUE 'B0872  '.
               10  FILLER           PIC X(7) VALUE 'B0879  '.
               10  FILLER           PIC X(7) VALUE 'B088   '.
               10  FILLER           PIC X(7) VALUE 'B09    '.
               10  FILLER           PIC X(7) VALUE 'B1001  '.
               10  FILLER           PIC X(7) VALUE 'B1009  '.
               10  FILLER           PIC X(7) VALUE 'B1081  '.
               10  FILLER           PIC X(7) VALUE 'B1082  '.
               10  FILLER           PIC X(7) VALUE 'B1089  '.
               10  FILLER           PIC X(7) VALUE 'B150   '.
               10  FILLER           PIC X(7) VALUE 'B159   '.
               10  FILLER           PIC X(7) VALUE 'B160   '.
               10  FILLER           PIC X(7) VALUE 'B161   '.
               10  FILLER           PIC X(7) VALUE 'B162   '.
               10  FILLER           PIC X(7) VALUE 'B169   '.
               10  FILLER           PIC X(7) VALUE 'B170   '.
               10  FILLER           PIC X(7) VALUE 'B1710  '.
               10  FILLER           PIC X(7) VALUE 'B1711  '.
               10  FILLER           PIC X(7) VALUE 'B172   '.
               10  FILLER           PIC X(7) VALUE 'B178   '.
               10  FILLER           PIC X(7) VALUE 'B179   '.
               10  FILLER           PIC X(7) VALUE 'B180   '.
               10  FILLER           PIC X(7) VALUE 'B181   '.
               10  FILLER           PIC X(7) VALUE 'B182   '.
               10  FILLER           PIC X(7) VALUE 'B188   '.
               10  FILLER           PIC X(7) VALUE 'B189   '.
               10  FILLER           PIC X(7) VALUE 'B190   '.
               10  FILLER           PIC X(7) VALUE 'B1910  '.
               10  FILLER           PIC X(7) VALUE 'B1911  '.
               10  FILLER           PIC X(7) VALUE 'B1920  '.
               10  FILLER           PIC X(7) VALUE 'B1921  '.
               10  FILLER           PIC X(7) VALUE 'B199   '.
               10  FILLER           PIC X(7) VALUE 'B20    '.
               10  FILLER           PIC X(7) VALUE 'B250   '.
               10  FILLER           PIC X(7) VALUE 'B251   '.
               10  FILLER           PIC X(7) VALUE 'B252   '.
               10  FILLER           PIC X(7) VALUE 'B258   '.
               10  FILLER           PIC X(7) VALUE 'B259   '.
               10  FILLER           PIC X(7) VALUE 'B260   '.
               10  FILLER           PIC X(7) VALUE 'B261   '.
               10  FILLER           PIC X(7) VALUE 'B262   '.
               10  FILLER           PIC X(7) VALUE 'B263   '.
               10  FILLER           PIC X(7) VALUE 'B2681  '.
               10  FILLER           PIC X(7) VALUE 'B2682  '.
               10  FILLER           PIC X(7) VALUE 'B2683  '.
               10  FILLER           PIC X(7) VALUE 'B2684  '.
               10  FILLER           PIC X(7) VALUE 'B2685  '.
               10  FILLER           PIC X(7) VALUE 'B2689  '.
               10  FILLER           PIC X(7) VALUE 'B269   '.
               10  FILLER           PIC X(7) VALUE 'B2700  '.
               10  FILLER           PIC X(7) VALUE 'B2701  '.
               10  FILLER           PIC X(7) VALUE 'B2702  '.
               10  FILLER           PIC X(7) VALUE 'B2709  '.
               10  FILLER           PIC X(7) VALUE 'B2710  '.
               10  FILLER           PIC X(7) VALUE 'B2711  '.
               10  FILLER           PIC X(7) VALUE 'B2712  '.
               10  FILLER           PIC X(7) VALUE 'B2719  '.
               10  FILLER           PIC X(7) VALUE 'B2780  '.
               10  FILLER           PIC X(7) VALUE 'B2781  '.
               10  FILLER           PIC X(7) VALUE 'B2782  '.
               10  FILLER           PIC X(7) VALUE 'B2789  '.
               10  FILLER           PIC X(7) VALUE 'B2790  '.
               10  FILLER           PIC X(7) VALUE 'B2791  '.
               10  FILLER           PIC X(7) VALUE 'B2792  '.
               10  FILLER           PIC X(7) VALUE 'B2799  '.
               10  FILLER           PIC X(7) VALUE 'B330   '.
               10  FILLER           PIC X(7) VALUE 'B331   '.
               10  FILLER           PIC X(7) VALUE 'B3320  '.
               10  FILLER           PIC X(7) VALUE 'B3321  '.
               10  FILLER           PIC X(7) VALUE 'B3322  '.
               10  FILLER           PIC X(7) VALUE 'B3323  '.
               10  FILLER           PIC X(7) VALUE 'B3324  '.
               10  FILLER           PIC X(7) VALUE 'B333   '.
               10  FILLER           PIC X(7) VALUE 'B338   '.
               10  FILLER           PIC X(7) VALUE 'B341   '.
               10  FILLER           PIC X(7) VALUE 'B471   '.
               10  FILLER           PIC X(7) VALUE 'B479   '.
               10  FILLER           PIC X(7) VALUE 'B6000  '.
               10  FILLER           PIC X(7) VALUE 'B6001  '.
               10  FILLER           PIC X(7) VALUE 'B6002  '.
               10  FILLER           PIC X(7) VALUE 'B6003  '.
               10  FILLER           PIC X(7) VALUE 'B6009  '.
               10  FILLER           PIC X(7) VALUE 'B950   '.
               10  FILLER           PIC X(7) VALUE 'B951   '.
               10  FILLER           PIC X(7) VALUE 'B952   '.
               10  FILLER           PIC X(7) VALUE 'B953   '.
               10  FILLER           PIC X(7) VALUE 'B954   '.
               10  FILLER           PIC X(7) VALUE 'B955   '.
               10  FILLER           PIC X(7) VALUE 'B958   '.
               10  FILLER           PIC X(7) VALUE 'B9730  '.
               10  FILLER           PIC X(7) VALUE 'B9731  '.
               10  FILLER           PIC X(7) VALUE 'B9732  '.
               10  FILLER           PIC X(7) VALUE 'B9733  '.
               10  FILLER           PIC X(7) VALUE 'B9734  '.
               10  FILLER           PIC X(7) VALUE 'B9735  '.
               10  FILLER           PIC X(7) VALUE 'B9739  '.
               10  FILLER           PIC X(7) VALUE 'G032   '.
               10  FILLER           PIC X(7) VALUE 'I673   '.
               10  FILLER           PIC X(7) VALUE 'J020   '.
               10  FILLER           PIC X(7) VALUE 'J0300  '.
               10  FILLER           PIC X(7) VALUE 'J0301  '.
               10  FILLER           PIC X(7) VALUE 'J202   '.
               10  FILLER           PIC X(7) VALUE 'K9081  '.
               10  FILLER           PIC X(7) VALUE 'L081   '.
               10  FILLER           PIC X(7) VALUE 'L444   '.
               10  FILLER           PIC X(7) VALUE 'M60009 '.
           05  FILLER     REDEFINES CAT10-TABLE-DATA.
               10  CAT10-DATA       OCCURS 444 TIMES
                                    ASCENDING KEY IS CAT10-CODE
                                    INDEXED BY IX-CAT10.
                   15  CAT10-CODE   PIC X(7).

      ****************************************************************
      * DRUG AND/OR ALCOHOL INDUCED MENTAL DISORDERS                 *
      ****************************************************************
       01 CAT11-TABLE.
           05  CAT11-TABLE-DATA.
               10  FILLER           PIC X(7) VALUE 'F10121 '.
               10  FILLER           PIC X(7) VALUE 'F10130 '.
               10  FILLER           PIC X(7) VALUE 'F10131 '.
               10  FILLER           PIC X(7) VALUE 'F10132 '.
               10  FILLER           PIC X(7) VALUE 'F10139 '.
               10  FILLER           PIC X(7) VALUE 'F10220 '.
               10  FILLER           PIC X(7) VALUE 'F10221 '.
               10  FILLER           PIC X(7) VALUE 'F10229 '.
               10  FILLER           PIC X(7) VALUE 'F10231 '.
               10  FILLER           PIC X(7) VALUE 'F10921 '.
               10  FILLER           PIC X(7) VALUE 'F10930 '.
               10  FILLER           PIC X(7) VALUE 'F10931 '.
               10  FILLER           PIC X(7) VALUE 'F10932 '.
               10  FILLER           PIC X(7) VALUE 'F10939 '.
               10  FILLER           PIC X(7) VALUE 'F1113  '.
               10  FILLER           PIC X(7) VALUE 'F11151 '.
               10  FILLER           PIC X(7) VALUE 'F1120  '.
               10  FILLER           PIC X(7) VALUE 'F11220 '.
               10  FILLER           PIC X(7) VALUE 'F11221 '.
               10  FILLER           PIC X(7) VALUE 'F11222 '.
               10  FILLER           PIC X(7) VALUE 'F11229 '.
               10  FILLER           PIC X(7) VALUE 'F1123  '.
               10  FILLER           PIC X(7) VALUE 'F1124  '.
               10  FILLER           PIC X(7) VALUE 'F11250 '.
               10  FILLER           PIC X(7) VALUE 'F11251 '.
               10  FILLER           PIC X(7) VALUE 'F11259 '.
               10  FILLER           PIC X(7) VALUE 'F11281 '.
               10  FILLER           PIC X(7) VALUE 'F11282 '.
               10  FILLER           PIC X(7) VALUE 'F11288 '.
               10  FILLER           PIC X(7) VALUE 'F1129  '.
               10  FILLER           PIC X(7) VALUE 'F11920 '.
               10  FILLER           PIC X(7) VALUE 'F11921 '.
               10  FILLER           PIC X(7) VALUE 'F11922 '.
               10  FILLER           PIC X(7) VALUE 'F11929 '.
               10  FILLER           PIC X(7) VALUE 'F1193  '.
               10  FILLER           PIC X(7) VALUE 'F11951 '.
               10  FILLER           PIC X(7) VALUE 'F12120 '.
               10  FILLER           PIC X(7) VALUE 'F12121 '.
               10  FILLER           PIC X(7) VALUE 'F12122 '.
               10  FILLER           PIC X(7) VALUE 'F12129 '.
               10  FILLER           PIC X(7) VALUE 'F1213  '.
               10  FILLER           PIC X(7) VALUE 'F12151 '.
               10  FILLER           PIC X(7) VALUE 'F12220 '.
               10  FILLER           PIC X(7) VALUE 'F12221 '.
               10  FILLER           PIC X(7) VALUE 'F12222 '.
               10  FILLER           PIC X(7) VALUE 'F12229 '.
               10  FILLER           PIC X(7) VALUE 'F1223  '.
               10  FILLER           PIC X(7) VALUE 'F12251 '.
               10  FILLER           PIC X(7) VALUE 'F12920 '.
               10  FILLER           PIC X(7) VALUE 'F12921 '.
               10  FILLER           PIC X(7) VALUE 'F12922 '.
               10  FILLER           PIC X(7) VALUE 'F12929 '.
               10  FILLER           PIC X(7) VALUE 'F1293  '.
               10  FILLER           PIC X(7) VALUE 'F12951 '.
               10  FILLER           PIC X(7) VALUE 'F13120 '.
               10  FILLER           PIC X(7) VALUE 'F13121 '.
               10  FILLER           PIC X(7) VALUE 'F13129 '.
               10  FILLER           PIC X(7) VALUE 'F13130 '.
               10  FILLER           PIC X(7) VALUE 'F13131 '.
               10  FILLER           PIC X(7) VALUE 'F13132 '.
               10  FILLER           PIC X(7) VALUE 'F13139 '.
               10  FILLER           PIC X(7) VALUE 'F13151 '.
               10  FILLER           PIC X(7) VALUE 'F13220 '.
               10  FILLER           PIC X(7) VALUE 'F13221 '.
               10  FILLER           PIC X(7) VALUE 'F13229 '.
               10  FILLER           PIC X(7) VALUE 'F13230 '.
               10  FILLER           PIC X(7) VALUE 'F13231 '.
               10  FILLER           PIC X(7) VALUE 'F13232 '.
               10  FILLER           PIC X(7) VALUE 'F13239 '.
               10  FILLER           PIC X(7) VALUE 'F13251 '.
               10  FILLER           PIC X(7) VALUE 'F13920 '.
               10  FILLER           PIC X(7) VALUE 'F13921 '.
               10  FILLER           PIC X(7) VALUE 'F13929 '.
               10  FILLER           PIC X(7) VALUE 'F13930 '.
               10  FILLER           PIC X(7) VALUE 'F13931 '.
               10  FILLER           PIC X(7) VALUE 'F13932 '.
               10  FILLER           PIC X(7) VALUE 'F13939 '.
               10  FILLER           PIC X(7) VALUE 'F13951 '.
               10  FILLER           PIC X(7) VALUE 'F14120 '.
               10  FILLER           PIC X(7) VALUE 'F14121 '.
               10  FILLER           PIC X(7) VALUE 'F14122 '.
               10  FILLER           PIC X(7) VALUE 'F14129 '.
               10  FILLER           PIC X(7) VALUE 'F1413  '.
               10  FILLER           PIC X(7) VALUE 'F14151 '.
               10  FILLER           PIC X(7) VALUE 'F14220 '.
               10  FILLER           PIC X(7) VALUE 'F14221 '.
               10  FILLER           PIC X(7) VALUE 'F14222 '.
               10  FILLER           PIC X(7) VALUE 'F14229 '.
               10  FILLER           PIC X(7) VALUE 'F1423  '.
               10  FILLER           PIC X(7) VALUE 'F14251 '.
               10  FILLER           PIC X(7) VALUE 'F14920 '.
               10  FILLER           PIC X(7) VALUE 'F14921 '.
               10  FILLER           PIC X(7) VALUE 'F14922 '.
               10  FILLER           PIC X(7) VALUE 'F14929 '.
               10  FILLER           PIC X(7) VALUE 'F1493  '.
               10  FILLER           PIC X(7) VALUE 'F14951 '.
               10  FILLER           PIC X(7) VALUE 'F15120 '.
               10  FILLER           PIC X(7) VALUE 'F15121 '.
               10  FILLER           PIC X(7) VALUE 'F15122 '.
               10  FILLER           PIC X(7) VALUE 'F15129 '.
               10  FILLER           PIC X(7) VALUE 'F1513  '.
               10  FILLER           PIC X(7) VALUE 'F15151 '.
               10  FILLER           PIC X(7) VALUE 'F15220 '.
               10  FILLER           PIC X(7) VALUE 'F15221 '.
               10  FILLER           PIC X(7) VALUE 'F15222 '.
               10  FILLER           PIC X(7) VALUE 'F15229 '.
               10  FILLER           PIC X(7) VALUE 'F1523  '.
               10  FILLER           PIC X(7) VALUE 'F15251 '.
               10  FILLER           PIC X(7) VALUE 'F15920 '.
               10  FILLER           PIC X(7) VALUE 'F15921 '.
               10  FILLER           PIC X(7) VALUE 'F15922 '.
               10  FILLER           PIC X(7) VALUE 'F15929 '.
               10  FILLER           PIC X(7) VALUE 'F1593  '.
               10  FILLER           PIC X(7) VALUE 'F15951 '.
               10  FILLER           PIC X(7) VALUE 'F16120 '.
               10  FILLER           PIC X(7) VALUE 'F16121 '.
               10  FILLER           PIC X(7) VALUE 'F16122 '.
               10  FILLER           PIC X(7) VALUE 'F16129 '.
               10  FILLER           PIC X(7) VALUE 'F16151 '.
               10  FILLER           PIC X(7) VALUE 'F16220 '.
               10  FILLER           PIC X(7) VALUE 'F16221 '.
               10  FILLER           PIC X(7) VALUE 'F16229 '.
               10  FILLER           PIC X(7) VALUE 'F16251 '.
               10  FILLER           PIC X(7) VALUE 'F16920 '.
               10  FILLER           PIC X(7) VALUE 'F16921 '.
               10  FILLER           PIC X(7) VALUE 'F16929 '.
               10  FILLER           PIC X(7) VALUE 'F16951 '.
               10  FILLER           PIC X(7) VALUE 'F17203 '.
               10  FILLER           PIC X(7) VALUE 'F17213 '.
               10  FILLER           PIC X(7) VALUE 'F17223 '.
               10  FILLER           PIC X(7) VALUE 'F17293 '.
               10  FILLER           PIC X(7) VALUE 'F18120 '.
               10  FILLER           PIC X(7) VALUE 'F18121 '.
               10  FILLER           PIC X(7) VALUE 'F18129 '.
               10  FILLER           PIC X(7) VALUE 'F18151 '.
               10  FILLER           PIC X(7) VALUE 'F18220 '.
               10  FILLER           PIC X(7) VALUE 'F18221 '.
               10  FILLER           PIC X(7) VALUE 'F18229 '.
               10  FILLER           PIC X(7) VALUE 'F18251 '.
               10  FILLER           PIC X(7) VALUE 'F18920 '.
               10  FILLER           PIC X(7) VALUE 'F18921 '.
               10  FILLER           PIC X(7) VALUE 'F18929 '.
               10  FILLER           PIC X(7) VALUE 'F18951 '.
               10  FILLER           PIC X(7) VALUE 'F19120 '.
               10  FILLER           PIC X(7) VALUE 'F19121 '.
               10  FILLER           PIC X(7) VALUE 'F19122 '.
               10  FILLER           PIC X(7) VALUE 'F19129 '.
               10  FILLER           PIC X(7) VALUE 'F19130 '.
               10  FILLER           PIC X(7) VALUE 'F19131 '.
               10  FILLER           PIC X(7) VALUE 'F19132 '.
               10  FILLER           PIC X(7) VALUE 'F19139 '.
               10  FILLER           PIC X(7) VALUE 'F19151 '.
               10  FILLER           PIC X(7) VALUE 'F19220 '.
               10  FILLER           PIC X(7) VALUE 'F19221 '.
               10  FILLER           PIC X(7) VALUE 'F19222 '.
               10  FILLER           PIC X(7) VALUE 'F19229 '.
               10  FILLER           PIC X(7) VALUE 'F19230 '.
               10  FILLER           PIC X(7) VALUE 'F19231 '.
               10  FILLER           PIC X(7) VALUE 'F19232 '.
               10  FILLER           PIC X(7) VALUE 'F19239 '.
               10  FILLER           PIC X(7) VALUE 'F19251 '.
               10  FILLER           PIC X(7) VALUE 'F19920 '.
               10  FILLER           PIC X(7) VALUE 'F19921 '.
               10  FILLER           PIC X(7) VALUE 'F19922 '.
               10  FILLER           PIC X(7) VALUE 'F19929 '.
               10  FILLER           PIC X(7) VALUE 'F19930 '.
               10  FILLER           PIC X(7) VALUE 'F19931 '.
               10  FILLER           PIC X(7) VALUE 'F19932 '.
               10  FILLER           PIC X(7) VALUE 'F19939 '.
               10  FILLER           PIC X(7) VALUE 'F19951 '.
           05  FILLER     REDEFINES CAT11-TABLE-DATA.
               10  CAT11-DATA       OCCURS 170 TIMES
                                    ASCENDING KEY IS CAT11-CODE
                                    INDEXED BY IX-CAT11.
                   15  CAT11-CODE   PIC X(7).

      ****************************************************************
      * CARDIAC CONDITIONS                                           *
      ****************************************************************
       01 CAT12-TABLE.
           05  CAT12-TABLE-DATA.
               10  FILLER           PIC X(7) VALUE 'I010   '.
               10  FILLER           PIC X(7) VALUE 'I011   '.
               10  FILLER           PIC X(7) VALUE 'I012   '.
               10  FILLER           PIC X(7) VALUE 'I110   '.
               10  FILLER           PIC X(7) VALUE 'I270   '.
               10  FILLER           PIC X(7) VALUE 'I330   '.
               10  FILLER           PIC X(7) VALUE 'I339   '.
               10  FILLER           PIC X(7) VALUE 'I39    '.
           05  FILLER     REDEFINES CAT12-TABLE-DATA.
               10  CAT12-DATA       OCCURS 8 TIMES
                                    ASCENDING KEY IS CAT12-CODE
                                    INDEXED BY IX-CAT12.
                   15  CAT12-CODE   PIC X(7).

      ****************************************************************
      * GANGRENE                                                     *
      ****************************************************************
       01 CAT13-TABLE.
           05  CAT13-TABLE-DATA.
               10  FILLER           PIC X(7) VALUE 'E0852  '.
               10  FILLER           PIC X(7) VALUE 'E0952  '.
               10  FILLER           PIC X(7) VALUE 'E1052  '.
               10  FILLER           PIC X(7) VALUE 'E1152  '.
               10  FILLER           PIC X(7) VALUE 'E1352  '.
               10  FILLER           PIC X(7) VALUE 'I70261 '.
               10  FILLER           PIC X(7) VALUE 'I70262 '.
               10  FILLER           PIC X(7) VALUE 'I70263 '.
               10  FILLER           PIC X(7) VALUE 'I70268 '.
               10  FILLER           PIC X(7) VALUE 'I70361 '.
               10  FILLER           PIC X(7) VALUE 'I70362 '.
               10  FILLER           PIC X(7) VALUE 'I70363 '.
               10  FILLER           PIC X(7) VALUE 'I70368 '.
               10  FILLER           PIC X(7) VALUE 'I70461 '.
               10  FILLER           PIC X(7) VALUE 'I70462 '.
               10  FILLER           PIC X(7) VALUE 'I70463 '.
               10  FILLER           PIC X(7) VALUE 'I70468 '.
               10  FILLER           PIC X(7) VALUE 'I70561 '.
               10  FILLER           PIC X(7) VALUE 'I70562 '.
               10  FILLER           PIC X(7) VALUE 'I70563 '.
               10  FILLER           PIC X(7) VALUE 'I70568 '.
               10  FILLER           PIC X(7) VALUE 'I70661 '.
               10  FILLER           PIC X(7) VALUE 'I70662 '.
               10  FILLER           PIC X(7) VALUE 'I70663 '.
               10  FILLER           PIC X(7) VALUE 'I70668 '.
               10  FILLER           PIC X(7) VALUE 'I70761 '.
               10  FILLER           PIC X(7) VALUE 'I70762 '.
               10  FILLER           PIC X(7) VALUE 'I70763 '.
               10  FILLER           PIC X(7) VALUE 'I70768 '.
               10  FILLER           PIC X(7) VALUE 'I7301  '.
               10  FILLER           PIC X(7) VALUE 'I96    '.
               10  FILLER           PIC X(7) VALUE 'K3531  '.
               10  FILLER           PIC X(7) VALUE 'K35891 '.
               10  FILLER           PIC X(7) VALUE 'K82A1  '.
           05  FILLER     REDEFINES CAT13-TABLE-DATA.
               10  CAT13-DATA       OCCURS 34 TIMES
                                    ASCENDING KEY IS CAT13-CODE
                                    INDEXED BY IX-CAT13.
                   15  CAT13-CODE   PIC X(7).

      ****************************************************************
      * CHRONIC OBSTRUCTIVE PULMONARY DISEASE - EFFECTIVE 10/01/2005 *
      ****************************************************************
       01 CAT14-TABLE.
           05  CAT14-TABLE-DATA.
               10  FILLER           PIC X(7) VALUE 'J441   '.
               10  FILLER           PIC X(7) VALUE 'J470   '.
               10  FILLER           PIC X(7) VALUE 'J471   '.
               10  FILLER           PIC X(7) VALUE 'J860   '.
               10  FILLER           PIC X(7) VALUE 'J95850 '.
               10  FILLER           PIC X(7) VALUE 'J9610  '.
               10  FILLER           PIC X(7) VALUE 'J9611  '.
               10  FILLER           PIC X(7) VALUE 'J9612  '.
               10  FILLER           PIC X(7) VALUE 'J9620  '.
               10  FILLER           PIC X(7) VALUE 'J9621  '.
               10  FILLER           PIC X(7) VALUE 'J9622  '.
               10  FILLER           PIC X(7) VALUE 'U071   '.
               10  FILLER           PIC X(7) VALUE 'Z9911  '.
               10  FILLER           PIC X(7) VALUE 'Z9912  '.
           05  FILLER     REDEFINES CAT14-TABLE-DATA.
               10  CAT14-DATA       OCCURS 14 TIMES
                                    ASCENDING KEY IS CAT14-CODE
                                    INDEXED BY IX-CAT14.
                   15  CAT14-CODE   PIC X(7).

      ****************************************************************
      * ARTIFICIAL OPENING - DIGESTIVE AND URINARY                   *
      ****************************************************************
       01 CAT15-TABLE.
           05  CAT15-TABLE-DATA.
               10  FILLER           PIC X(7) VALUE 'K9400  '.
               10  FILLER           PIC X(7) VALUE 'K9401  '.
               10  FILLER           PIC X(7) VALUE 'K9402  '.
               10  FILLER           PIC X(7) VALUE 'K9403  '.
               10  FILLER           PIC X(7) VALUE 'K9409  '.
               10  FILLER           PIC X(7) VALUE 'K9410  '.
               10  FILLER           PIC X(7) VALUE 'K9411  '.
               10  FILLER           PIC X(7) VALUE 'K9412  '.
               10  FILLER           PIC X(7) VALUE 'K9413  '.
               10  FILLER           PIC X(7) VALUE 'K9419  '.
               10  FILLER           PIC X(7) VALUE 'N990   '.
               10  FILLER           PIC X(7) VALUE 'N99520 '.
               10  FILLER           PIC X(7) VALUE 'N99521 '.
               10  FILLER           PIC X(7) VALUE 'N99522 '.
               10  FILLER           PIC X(7) VALUE 'N99523 '.
               10  FILLER           PIC X(7) VALUE 'N99524 '.
               10  FILLER           PIC X(7) VALUE 'N99528 '.
               10  FILLER           PIC X(7) VALUE 'N99530 '.
               10  FILLER           PIC X(7) VALUE 'N99531 '.
               10  FILLER           PIC X(7) VALUE 'N99532 '.
               10  FILLER           PIC X(7) VALUE 'N99533 '.
               10  FILLER           PIC X(7) VALUE 'N99534 '.
               10  FILLER           PIC X(7) VALUE 'N99538 '.
               10  FILLER           PIC X(7) VALUE 'N9981  '.
               10  FILLER           PIC X(7) VALUE 'N9989  '.
               10  FILLER           PIC X(7) VALUE 'Z931   '.
               10  FILLER           PIC X(7) VALUE 'Z932   '.
               10  FILLER           PIC X(7) VALUE 'Z933   '.
               10  FILLER           PIC X(7) VALUE 'Z934   '.
               10  FILLER           PIC X(7) VALUE 'Z9350  '.
               10  FILLER           PIC X(7) VALUE 'Z9351  '.
               10  FILLER           PIC X(7) VALUE 'Z9352  '.
               10  FILLER           PIC X(7) VALUE 'Z9359  '.
               10  FILLER           PIC X(7) VALUE 'Z936   '.
           05  FILLER     REDEFINES CAT15-TABLE-DATA.
               10  CAT15-DATA       OCCURS 34 TIMES
                                    ASCENDING KEY IS CAT15-CODE
                                    INDEXED BY IX-CAT15.
                   15  CAT15-CODE   PIC X(7).

      ****************************************************************
      * SEVERE MUSCLOSKELETAL AND CONNECTIVE TISSUE                  *
      ****************************************************************
       01 CAT16-TABLE.
           05  CAT16-TABLE-DATA.
               10  FILLER           PIC X(7) VALUE 'L4050  '.
               10  FILLER           PIC X(7) VALUE 'L4051  '.
               10  FILLER           PIC X(7) VALUE 'L4052  '.
               10  FILLER           PIC X(7) VALUE 'L4053  '.
               10  FILLER           PIC X(7) VALUE 'L4054  '.
               10  FILLER           PIC X(7) VALUE 'L4059  '.
               10  FILLER           PIC X(7) VALUE 'M320   '.
               10  FILLER           PIC X(7) VALUE 'M3210  '.
               10  FILLER           PIC X(7) VALUE 'M3211  '.
               10  FILLER           PIC X(7) VALUE 'M3212  '.
               10  FILLER           PIC X(7) VALUE 'M3213  '.
               10  FILLER           PIC X(7) VALUE 'M3214  '.
               10  FILLER           PIC X(7) VALUE 'M3215  '.
               10  FILLER           PIC X(7) VALUE 'M3219  '.
               10  FILLER           PIC X(7) VALUE 'M328   '.
               10  FILLER           PIC X(7) VALUE 'M329   '.
               10  FILLER           PIC X(7) VALUE 'M4620  '.
               10  FILLER           PIC X(7) VALUE 'M4621  '.
               10  FILLER           PIC X(7) VALUE 'M4622  '.
               10  FILLER           PIC X(7) VALUE 'M4623  '.
               10  FILLER           PIC X(7) VALUE 'M4624  '.
               10  FILLER           PIC X(7) VALUE 'M4625  '.
               10  FILLER           PIC X(7) VALUE 'M4626  '.
               10  FILLER           PIC X(7) VALUE 'M4627  '.
               10  FILLER           PIC X(7) VALUE 'M4628  '.
               10  FILLER           PIC X(7) VALUE 'M86011 '.
               10  FILLER           PIC X(7) VALUE 'M86012 '.
               10  FILLER           PIC X(7) VALUE 'M86021 '.
               10  FILLER           PIC X(7) VALUE 'M86022 '.
               10  FILLER           PIC X(7) VALUE 'M86031 '.
               10  FILLER           PIC X(7) VALUE 'M86032 '.
               10  FILLER           PIC X(7) VALUE 'M86041 '.
               10  FILLER           PIC X(7) VALUE 'M86042 '.
               10  FILLER           PIC X(7) VALUE 'M86051 '.
               10  FILLER           PIC X(7) VALUE 'M86052 '.
               10  FILLER           PIC X(7) VALUE 'M86061 '.
               10  FILLER           PIC X(7) VALUE 'M86062 '.
               10  FILLER           PIC X(7) VALUE 'M86071 '.
               10  FILLER           PIC X(7) VALUE 'M86072 '.
               10  FILLER           PIC X(7) VALUE 'M8608  '.
               10  FILLER           PIC X(7) VALUE 'M8609  '.
               10  FILLER           PIC X(7) VALUE 'M86111 '.
               10  FILLER           PIC X(7) VALUE 'M86112 '.
               10  FILLER           PIC X(7) VALUE 'M86121 '.
               10  FILLER           PIC X(7) VALUE 'M86122 '.
               10  FILLER           PIC X(7) VALUE 'M86131 '.
               10  FILLER           PIC X(7) VALUE 'M86132 '.
               10  FILLER           PIC X(7) VALUE 'M86141 '.
               10  FILLER           PIC X(7) VALUE 'M86142 '.
               10  FILLER           PIC X(7) VALUE 'M86151 '.
               10  FILLER           PIC X(7) VALUE 'M86152 '.
               10  FILLER           PIC X(7) VALUE 'M86161 '.
               10  FILLER           PIC X(7) VALUE 'M86162 '.
               10  FILLER           PIC X(7) VALUE 'M86171 '.
               10  FILLER           PIC X(7) VALUE 'M86172 '.
               10  FILLER           PIC X(7) VALUE 'M8618  '.
               10  FILLER           PIC X(7) VALUE 'M8619  '.
               10  FILLER           PIC X(7) VALUE 'M86211 '.
               10  FILLER           PIC X(7) VALUE 'M86212 '.
               10  FILLER           PIC X(7) VALUE 'M86221 '.
               10  FILLER           PIC X(7) VALUE 'M86222 '.
               10  FILLER           PIC X(7) VALUE 'M86231 '.
               10  FILLER           PIC X(7) VALUE 'M86232 '.
               10  FILLER           PIC X(7) VALUE 'M86241 '.
               10  FILLER           PIC X(7) VALUE 'M86242 '.
               10  FILLER           PIC X(7) VALUE 'M86251 '.
               10  FILLER           PIC X(7) VALUE 'M86252 '.
               10  FILLER           PIC X(7) VALUE 'M86261 '.
               10  FILLER           PIC X(7) VALUE 'M86262 '.
               10  FILLER           PIC X(7) VALUE 'M86271 '.
               10  FILLER           PIC X(7) VALUE 'M86272 '.
               10  FILLER           PIC X(7) VALUE 'M8628  '.
               10  FILLER           PIC X(7) VALUE 'M8629  '.
               10  FILLER           PIC X(7) VALUE 'M86311 '.
               10  FILLER           PIC X(7) VALUE 'M86312 '.
               10  FILLER           PIC X(7) VALUE 'M86321 '.
               10  FILLER           PIC X(7) VALUE 'M86322 '.
               10  FILLER           PIC X(7) VALUE 'M86331 '.
               10  FILLER           PIC X(7) VALUE 'M86332 '.
               10  FILLER           PIC X(7) VALUE 'M86341 '.
               10  FILLER           PIC X(7) VALUE 'M86342 '.
               10  FILLER           PIC X(7) VALUE 'M86351 '.
               10  FILLER           PIC X(7) VALUE 'M86352 '.
               10  FILLER           PIC X(7) VALUE 'M86361 '.
               10  FILLER           PIC X(7) VALUE 'M86362 '.
               10  FILLER           PIC X(7) VALUE 'M86371 '.
               10  FILLER           PIC X(7) VALUE 'M86372 '.
               10  FILLER           PIC X(7) VALUE 'M8638  '.
               10  FILLER           PIC X(7) VALUE 'M8639  '.
               10  FILLER           PIC X(7) VALUE 'M86411 '.
               10  FILLER           PIC X(7) VALUE 'M86412 '.
               10  FILLER           PIC X(7) VALUE 'M86421 '.
               10  FILLER           PIC X(7) VALUE 'M86422 '.
               10  FILLER           PIC X(7) VALUE 'M86431 '.
               10  FILLER           PIC X(7) VALUE 'M86432 '.
               10  FILLER           PIC X(7) VALUE 'M86441 '.
               10  FILLER           PIC X(7) VALUE 'M86442 '.
               10  FILLER           PIC X(7) VALUE 'M86451 '.
               10  FILLER           PIC X(7) VALUE 'M86452 '.
               10  FILLER           PIC X(7) VALUE 'M86461 '.
               10  FILLER           PIC X(7) VALUE 'M86462 '.
               10  FILLER           PIC X(7) VALUE 'M86471 '.
               10  FILLER           PIC X(7) VALUE 'M86472 '.
               10  FILLER           PIC X(7) VALUE 'M8648  '.
               10  FILLER           PIC X(7) VALUE 'M8649  '.
               10  FILLER           PIC X(7) VALUE 'M86511 '.
               10  FILLER           PIC X(7) VALUE 'M86512 '.
               10  FILLER           PIC X(7) VALUE 'M86521 '.
               10  FILLER           PIC X(7) VALUE 'M86522 '.
               10  FILLER           PIC X(7) VALUE 'M86531 '.
               10  FILLER           PIC X(7) VALUE 'M86532 '.
               10  FILLER           PIC X(7) VALUE 'M86541 '.
               10  FILLER           PIC X(7) VALUE 'M86542 '.
               10  FILLER           PIC X(7) VALUE 'M86551 '.
               10  FILLER           PIC X(7) VALUE 'M86552 '.
               10  FILLER           PIC X(7) VALUE 'M86561 '.
               10  FILLER           PIC X(7) VALUE 'M86562 '.
               10  FILLER           PIC X(7) VALUE 'M86571 '.
               10  FILLER           PIC X(7) VALUE 'M86572 '.
               10  FILLER           PIC X(7) VALUE 'M8658  '.
               10  FILLER           PIC X(7) VALUE 'M8659  '.
               10  FILLER           PIC X(7) VALUE 'M86611 '.
               10  FILLER           PIC X(7) VALUE 'M86612 '.
               10  FILLER           PIC X(7) VALUE 'M86621 '.
               10  FILLER           PIC X(7) VALUE 'M86622 '.
               10  FILLER           PIC X(7) VALUE 'M86631 '.
               10  FILLER           PIC X(7) VALUE 'M86632 '.
               10  FILLER           PIC X(7) VALUE 'M86641 '.
               10  FILLER           PIC X(7) VALUE 'M86642 '.
               10  FILLER           PIC X(7) VALUE 'M86651 '.
               10  FILLER           PIC X(7) VALUE 'M86652 '.
               10  FILLER           PIC X(7) VALUE 'M86661 '.
               10  FILLER           PIC X(7) VALUE 'M86662 '.
               10  FILLER           PIC X(7) VALUE 'M86671 '.
               10  FILLER           PIC X(7) VALUE 'M86672 '.
               10  FILLER           PIC X(7) VALUE 'M8668  '.
               10  FILLER           PIC X(7) VALUE 'M8669  '.
               10  FILLER           PIC X(7) VALUE 'M868X0 '.
               10  FILLER           PIC X(7) VALUE 'M868X1 '.
               10  FILLER           PIC X(7) VALUE 'M868X2 '.
               10  FILLER           PIC X(7) VALUE 'M868X3 '.
               10  FILLER           PIC X(7) VALUE 'M868X4 '.
               10  FILLER           PIC X(7) VALUE 'M868X5 '.
               10  FILLER           PIC X(7) VALUE 'M868X6 '.
               10  FILLER           PIC X(7) VALUE 'M868X7 '.
               10  FILLER           PIC X(7) VALUE 'M868X8 '.
               10  FILLER           PIC X(7) VALUE 'M869   '.
           05  FILLER     REDEFINES CAT16-TABLE-DATA.
               10  CAT16-DATA       OCCURS 147 TIMES
                                    ASCENDING KEY IS CAT16-CODE
                                    INDEXED BY IX-CAT16.
                   15  CAT16-CODE   PIC X(7).

      ****************************************************************
      * POISONING                                                    *
      ****************************************************************
       01 CAT17-TABLE.
           05  CAT17-TABLE-DATA.
               10  FILLER           PIC X(7) VALUE 'T391X1A'.
               10  FILLER           PIC X(7) VALUE 'T391X2A'.
               10  FILLER           PIC X(7) VALUE 'T391X3A'.
               10  FILLER           PIC X(7) VALUE 'T391X4A'.
               10  FILLER           PIC X(7) VALUE 'T400X1A'.
               10  FILLER           PIC X(7) VALUE 'T400X2A'.
               10  FILLER           PIC X(7) VALUE 'T400X3A'.
               10  FILLER           PIC X(7) VALUE 'T400X4A'.
               10  FILLER           PIC X(7) VALUE 'T401X1A'.
               10  FILLER           PIC X(7) VALUE 'T401X2A'.
               10  FILLER           PIC X(7) VALUE 'T401X3A'.
               10  FILLER           PIC X(7) VALUE 'T401X4A'.
               10  FILLER           PIC X(7) VALUE 'T402X1A'.
               10  FILLER           PIC X(7) VALUE 'T402X2A'.
               10  FILLER           PIC X(7) VALUE 'T402X3A'.
               10  FILLER           PIC X(7) VALUE 'T402X4A'.
               10  FILLER           PIC X(7) VALUE 'T403X1A'.
               10  FILLER           PIC X(7) VALUE 'T403X2A'.
               10  FILLER           PIC X(7) VALUE 'T403X3A'.
               10  FILLER           PIC X(7) VALUE 'T403X4A'.
               10  FILLER           PIC X(7) VALUE 'T40411A'.
               10  FILLER           PIC X(7) VALUE 'T40412A'.
               10  FILLER           PIC X(7) VALUE 'T40413A'.
               10  FILLER           PIC X(7) VALUE 'T40414A'.
               10  FILLER           PIC X(7) VALUE 'T40421A'.
               10  FILLER           PIC X(7) VALUE 'T40422A'.
               10  FILLER           PIC X(7) VALUE 'T40423A'.
               10  FILLER           PIC X(7) VALUE 'T40424A'.
               10  FILLER           PIC X(7) VALUE 'T40491A'.
               10  FILLER           PIC X(7) VALUE 'T40492A'.
               10  FILLER           PIC X(7) VALUE 'T40493A'.
               10  FILLER           PIC X(7) VALUE 'T40494A'.
               10  FILLER           PIC X(7) VALUE 'T40601A'.
               10  FILLER           PIC X(7) VALUE 'T40602A'.
               10  FILLER           PIC X(7) VALUE 'T40603A'.
               10  FILLER           PIC X(7) VALUE 'T40604A'.
               10  FILLER           PIC X(7) VALUE 'T40691A'.
               10  FILLER           PIC X(7) VALUE 'T40692A'.
               10  FILLER           PIC X(7) VALUE 'T40693A'.
               10  FILLER           PIC X(7) VALUE 'T40694A'.
               10  FILLER           PIC X(7) VALUE 'T40711A'.
               10  FILLER           PIC X(7) VALUE 'T40712A'.
               10  FILLER           PIC X(7) VALUE 'T40713A'.
               10  FILLER           PIC X(7) VALUE 'T40714A'.
               10  FILLER           PIC X(7) VALUE 'T40721A'.
               10  FILLER           PIC X(7) VALUE 'T40722A'.
               10  FILLER           PIC X(7) VALUE 'T40723A'.
               10  FILLER           PIC X(7) VALUE 'T40724A'.
               10  FILLER           PIC X(7) VALUE 'T408X1A'.
               10  FILLER           PIC X(7) VALUE 'T408X2A'.
               10  FILLER           PIC X(7) VALUE 'T408X3A'.
               10  FILLER           PIC X(7) VALUE 'T408X4A'.
               10  FILLER           PIC X(7) VALUE 'T40901A'.
               10  FILLER           PIC X(7) VALUE 'T40902A'.
               10  FILLER           PIC X(7) VALUE 'T40903A'.
               10  FILLER           PIC X(7) VALUE 'T40904A'.
               10  FILLER           PIC X(7) VALUE 'T40991A'.
               10  FILLER           PIC X(7) VALUE 'T40992A'.
               10  FILLER           PIC X(7) VALUE 'T40993A'.
               10  FILLER           PIC X(7) VALUE 'T40994A'.
               10  FILLER           PIC X(7) VALUE 'T410X1A'.
               10  FILLER           PIC X(7) VALUE 'T410X2A'.
               10  FILLER           PIC X(7) VALUE 'T410X3A'.
               10  FILLER           PIC X(7) VALUE 'T410X4A'.
               10  FILLER           PIC X(7) VALUE 'T411X1A'.
               10  FILLER           PIC X(7) VALUE 'T411X2A'.
               10  FILLER           PIC X(7) VALUE 'T411X3A'.
               10  FILLER           PIC X(7) VALUE 'T411X4A'.
               10  FILLER           PIC X(7) VALUE 'T41201A'.
               10  FILLER           PIC X(7) VALUE 'T41202A'.
               10  FILLER           PIC X(7) VALUE 'T41203A'.
               10  FILLER           PIC X(7) VALUE 'T41204A'.
               10  FILLER           PIC X(7) VALUE 'T41291A'.
               10  FILLER           PIC X(7) VALUE 'T41292A'.
               10  FILLER           PIC X(7) VALUE 'T41293A'.
               10  FILLER           PIC X(7) VALUE 'T41294A'.
               10  FILLER           PIC X(7) VALUE 'T413X1A'.
               10  FILLER           PIC X(7) VALUE 'T413X2A'.
               10  FILLER           PIC X(7) VALUE 'T413X3A'.
               10  FILLER           PIC X(7) VALUE 'T413X4A'.
               10  FILLER           PIC X(7) VALUE 'T4141XA'.
               10  FILLER           PIC X(7) VALUE 'T4142XA'.
               10  FILLER           PIC X(7) VALUE 'T4143XA'.
               10  FILLER           PIC X(7) VALUE 'T4144XA'.
               10  FILLER           PIC X(7) VALUE 'T423X1A'.
               10  FILLER           PIC X(7) VALUE 'T423X2A'.
               10  FILLER           PIC X(7) VALUE 'T423X3A'.
               10  FILLER           PIC X(7) VALUE 'T423X4A'.
               10  FILLER           PIC X(7) VALUE 'T424X1A'.
               10  FILLER           PIC X(7) VALUE 'T424X2A'.
               10  FILLER           PIC X(7) VALUE 'T424X3A'.
               10  FILLER           PIC X(7) VALUE 'T424X4A'.
               10  FILLER           PIC X(7) VALUE 'T426X1A'.
               10  FILLER           PIC X(7) VALUE 'T426X2A'.
               10  FILLER           PIC X(7) VALUE 'T426X3A'.
               10  FILLER           PIC X(7) VALUE 'T426X4A'.
               10  FILLER           PIC X(7) VALUE 'T4271XA'.
               10  FILLER           PIC X(7) VALUE 'T4272XA'.
               10  FILLER           PIC X(7) VALUE 'T4273XA'.
               10  FILLER           PIC X(7) VALUE 'T4274XA'.
               10  FILLER           PIC X(7) VALUE 'T428X1A'.
               10  FILLER           PIC X(7) VALUE 'T428X2A'.
               10  FILLER           PIC X(7) VALUE 'T428X3A'.
               10  FILLER           PIC X(7) VALUE 'T428X4A'.
               10  FILLER           PIC X(7) VALUE 'T43011A'.
               10  FILLER           PIC X(7) VALUE 'T43012A'.
               10  FILLER           PIC X(7) VALUE 'T43013A'.
               10  FILLER           PIC X(7) VALUE 'T43014A'.
               10  FILLER           PIC X(7) VALUE 'T43021A'.
               10  FILLER           PIC X(7) VALUE 'T43022A'.
               10  FILLER           PIC X(7) VALUE 'T43023A'.
               10  FILLER           PIC X(7) VALUE 'T43024A'.
               10  FILLER           PIC X(7) VALUE 'T431X1A'.
               10  FILLER           PIC X(7) VALUE 'T431X2A'.
               10  FILLER           PIC X(7) VALUE 'T431X3A'.
               10  FILLER           PIC X(7) VALUE 'T431X4A'.
               10  FILLER           PIC X(7) VALUE 'T43201A'.
               10  FILLER           PIC X(7) VALUE 'T43202A'.
               10  FILLER           PIC X(7) VALUE 'T43203A'.
               10  FILLER           PIC X(7) VALUE 'T43204A'.
               10  FILLER           PIC X(7) VALUE 'T43211A'.
               10  FILLER           PIC X(7) VALUE 'T43212A'.
               10  FILLER           PIC X(7) VALUE 'T43213A'.
               10  FILLER           PIC X(7) VALUE 'T43214A'.
               10  FILLER           PIC X(7) VALUE 'T43221A'.
               10  FILLER           PIC X(7) VALUE 'T43222A'.
               10  FILLER           PIC X(7) VALUE 'T43223A'.
               10  FILLER           PIC X(7) VALUE 'T43224A'.
               10  FILLER           PIC X(7) VALUE 'T43291A'.
               10  FILLER           PIC X(7) VALUE 'T43292A'.
               10  FILLER           PIC X(7) VALUE 'T43293A'.
               10  FILLER           PIC X(7) VALUE 'T43294A'.
               10  FILLER           PIC X(7) VALUE 'T433X1A'.
               10  FILLER           PIC X(7) VALUE 'T433X2A'.
               10  FILLER           PIC X(7) VALUE 'T433X3A'.
               10  FILLER           PIC X(7) VALUE 'T433X4A'.
               10  FILLER           PIC X(7) VALUE 'T434X1A'.
               10  FILLER           PIC X(7) VALUE 'T434X2A'.
               10  FILLER           PIC X(7) VALUE 'T434X3A'.
               10  FILLER           PIC X(7) VALUE 'T434X4A'.
               10  FILLER           PIC X(7) VALUE 'T43501A'.
               10  FILLER           PIC X(7) VALUE 'T43502A'.
               10  FILLER           PIC X(7) VALUE 'T43503A'.
               10  FILLER           PIC X(7) VALUE 'T43504A'.
               10  FILLER           PIC X(7) VALUE 'T43591A'.
               10  FILLER           PIC X(7) VALUE 'T43592A'.
               10  FILLER           PIC X(7) VALUE 'T43593A'.
               10  FILLER           PIC X(7) VALUE 'T43594A'.
               10  FILLER           PIC X(7) VALUE 'T43601A'.
               10  FILLER           PIC X(7) VALUE 'T43602A'.
               10  FILLER           PIC X(7) VALUE 'T43603A'.
               10  FILLER           PIC X(7) VALUE 'T43604A'.
               10  FILLER           PIC X(7) VALUE 'T43611A'.
               10  FILLER           PIC X(7) VALUE 'T43612A'.
               10  FILLER           PIC X(7) VALUE 'T43613A'.
               10  FILLER           PIC X(7) VALUE 'T43614A'.
               10  FILLER           PIC X(7) VALUE 'T43621A'.
               10  FILLER           PIC X(7) VALUE 'T43622A'.
               10  FILLER           PIC X(7) VALUE 'T43623A'.
               10  FILLER           PIC X(7) VALUE 'T43624A'.
               10  FILLER           PIC X(7) VALUE 'T43631A'.
               10  FILLER           PIC X(7) VALUE 'T43632A'.
               10  FILLER           PIC X(7) VALUE 'T43633A'.
               10  FILLER           PIC X(7) VALUE 'T43634A'.
               10  FILLER           PIC X(7) VALUE 'T43641A'.
               10  FILLER           PIC X(7) VALUE 'T43642A'.
               10  FILLER           PIC X(7) VALUE 'T43643A'.
               10  FILLER           PIC X(7) VALUE 'T43644A'.
               10  FILLER           PIC X(7) VALUE 'T43691A'.
               10  FILLER           PIC X(7) VALUE 'T43692A'.
               10  FILLER           PIC X(7) VALUE 'T43693A'.
               10  FILLER           PIC X(7) VALUE 'T43694A'.
               10  FILLER           PIC X(7) VALUE 'T438X1A'.
               10  FILLER           PIC X(7) VALUE 'T438X2A'.
               10  FILLER           PIC X(7) VALUE 'T438X3A'.
               10  FILLER           PIC X(7) VALUE 'T438X4A'.
               10  FILLER           PIC X(7) VALUE 'T4391XA'.
               10  FILLER           PIC X(7) VALUE 'T4392XA'.
               10  FILLER           PIC X(7) VALUE 'T4393XA'.
               10  FILLER           PIC X(7) VALUE 'T4394XA'.
               10  FILLER           PIC X(7) VALUE 'T505X1A'.
               10  FILLER           PIC X(7) VALUE 'T505X2A'.
               10  FILLER           PIC X(7) VALUE 'T505X3A'.
               10  FILLER           PIC X(7) VALUE 'T505X4A'.
               10  FILLER           PIC X(7) VALUE 'T50911A'.
               10  FILLER           PIC X(7) VALUE 'T50912A'.
               10  FILLER           PIC X(7) VALUE 'T50913A'.
               10  FILLER           PIC X(7) VALUE 'T50914A'.
               10  FILLER           PIC X(7) VALUE 'T510X1A'.
               10  FILLER           PIC X(7) VALUE 'T510X2A'.
               10  FILLER           PIC X(7) VALUE 'T510X3A'.
               10  FILLER           PIC X(7) VALUE 'T510X4A'.
               10  FILLER           PIC X(7) VALUE 'T511X1A'.
               10  FILLER           PIC X(7) VALUE 'T511X2A'.
               10  FILLER           PIC X(7) VALUE 'T511X3A'.
               10  FILLER           PIC X(7) VALUE 'T511X4A'.
               10  FILLER           PIC X(7) VALUE 'T512X1A'.
               10  FILLER           PIC X(7) VALUE 'T512X2A'.
               10  FILLER           PIC X(7) VALUE 'T512X3A'.
               10  FILLER           PIC X(7) VALUE 'T512X4A'.
               10  FILLER           PIC X(7) VALUE 'T513X1A'.
               10  FILLER           PIC X(7) VALUE 'T513X2A'.
               10  FILLER           PIC X(7) VALUE 'T513X3A'.
               10  FILLER           PIC X(7) VALUE 'T513X4A'.
               10  FILLER           PIC X(7) VALUE 'T518X1A'.
               10  FILLER           PIC X(7) VALUE 'T518X2A'.
               10  FILLER           PIC X(7) VALUE 'T518X3A'.
               10  FILLER           PIC X(7) VALUE 'T518X4A'.
               10  FILLER           PIC X(7) VALUE 'T5191XA'.
               10  FILLER           PIC X(7) VALUE 'T5192XA'.
               10  FILLER           PIC X(7) VALUE 'T5193XA'.
               10  FILLER           PIC X(7) VALUE 'T5194XA'.
               10  FILLER           PIC X(7) VALUE 'T5391XA'.
               10  FILLER           PIC X(7) VALUE 'T5392XA'.
               10  FILLER           PIC X(7) VALUE 'T5393XA'.
               10  FILLER           PIC X(7) VALUE 'T5394XA'.
               10  FILLER           PIC X(7) VALUE 'T540X1A'.
               10  FILLER           PIC X(7) VALUE 'T540X2A'.
               10  FILLER           PIC X(7) VALUE 'T540X3A'.
               10  FILLER           PIC X(7) VALUE 'T540X4A'.
               10  FILLER           PIC X(7) VALUE 'T541X1A'.
               10  FILLER           PIC X(7) VALUE 'T541X2A'.
               10  FILLER           PIC X(7) VALUE 'T541X3A'.
               10  FILLER           PIC X(7) VALUE 'T541X4A'.
               10  FILLER           PIC X(7) VALUE 'T542X1A'.
               10  FILLER           PIC X(7) VALUE 'T542X2A'.
               10  FILLER           PIC X(7) VALUE 'T542X3A'.
               10  FILLER           PIC X(7) VALUE 'T542X4A'.
               10  FILLER           PIC X(7) VALUE 'T543X1A'.
               10  FILLER           PIC X(7) VALUE 'T543X2A'.
               10  FILLER           PIC X(7) VALUE 'T543X3A'.
               10  FILLER           PIC X(7) VALUE 'T543X4A'.
               10  FILLER           PIC X(7) VALUE 'T5491XA'.
               10  FILLER           PIC X(7) VALUE 'T5492XA'.
               10  FILLER           PIC X(7) VALUE 'T5493XA'.
               10  FILLER           PIC X(7) VALUE 'T5494XA'.
               10  FILLER           PIC X(7) VALUE 'T550X1A'.
               10  FILLER           PIC X(7) VALUE 'T550X2A'.
               10  FILLER           PIC X(7) VALUE 'T550X3A'.
               10  FILLER           PIC X(7) VALUE 'T550X4A'.
               10  FILLER           PIC X(7) VALUE 'T551X1A'.
               10  FILLER           PIC X(7) VALUE 'T551X2A'.
               10  FILLER           PIC X(7) VALUE 'T551X3A'.
               10  FILLER           PIC X(7) VALUE 'T551X4A'.
               10  FILLER           PIC X(7) VALUE 'T560X1A'.
               10  FILLER           PIC X(7) VALUE 'T560X2A'.
               10  FILLER           PIC X(7) VALUE 'T560X3A'.
               10  FILLER           PIC X(7) VALUE 'T560X4A'.
               10  FILLER           PIC X(7) VALUE 'T571X1A'.
               10  FILLER           PIC X(7) VALUE 'T571X2A'.
               10  FILLER           PIC X(7) VALUE 'T571X3A'.
               10  FILLER           PIC X(7) VALUE 'T571X4A'.
               10  FILLER           PIC X(7) VALUE 'T5801XA'.
               10  FILLER           PIC X(7) VALUE 'T5802XA'.
               10  FILLER           PIC X(7) VALUE 'T5803XA'.
               10  FILLER           PIC X(7) VALUE 'T5804XA'.
               10  FILLER           PIC X(7) VALUE 'T5811XA'.
               10  FILLER           PIC X(7) VALUE 'T5812XA'.
               10  FILLER           PIC X(7) VALUE 'T5813XA'.
               10  FILLER           PIC X(7) VALUE 'T5814XA'.
               10  FILLER           PIC X(7) VALUE 'T582X1A'.
               10  FILLER           PIC X(7) VALUE 'T582X2A'.
               10  FILLER           PIC X(7) VALUE 'T582X3A'.
               10  FILLER           PIC X(7) VALUE 'T582X4A'.
               10  FILLER           PIC X(7) VALUE 'T588X1A'.
               10  FILLER           PIC X(7) VALUE 'T588X2A'.
               10  FILLER           PIC X(7) VALUE 'T588X3A'.
               10  FILLER           PIC X(7) VALUE 'T588X4A'.
               10  FILLER           PIC X(7) VALUE 'T5891XA'.
               10  FILLER           PIC X(7) VALUE 'T5892XA'.
               10  FILLER           PIC X(7) VALUE 'T5893XA'.
               10  FILLER           PIC X(7) VALUE 'T5894XA'.
               10  FILLER           PIC X(7) VALUE 'T600X1A'.
               10  FILLER           PIC X(7) VALUE 'T600X2A'.
               10  FILLER           PIC X(7) VALUE 'T600X3A'.
               10  FILLER           PIC X(7) VALUE 'T600X4A'.
               10  FILLER           PIC X(7) VALUE 'T601X1A'.
               10  FILLER           PIC X(7) VALUE 'T601X2A'.
               10  FILLER           PIC X(7) VALUE 'T601X3A'.
               10  FILLER           PIC X(7) VALUE 'T601X4A'.
               10  FILLER           PIC X(7) VALUE 'T602X1A'.
               10  FILLER           PIC X(7) VALUE 'T602X2A'.
               10  FILLER           PIC X(7) VALUE 'T602X3A'.
               10  FILLER           PIC X(7) VALUE 'T602X4A'.
               10  FILLER           PIC X(7) VALUE 'T604X1A'.
               10  FILLER           PIC X(7) VALUE 'T604X2A'.
               10  FILLER           PIC X(7) VALUE 'T604X3A'.
               10  FILLER           PIC X(7) VALUE 'T604X4A'.
               10  FILLER           PIC X(7) VALUE 'T608X1A'.
               10  FILLER           PIC X(7) VALUE 'T608X2A'.
               10  FILLER           PIC X(7) VALUE 'T608X3A'.
               10  FILLER           PIC X(7) VALUE 'T608X4A'.
               10  FILLER           PIC X(7) VALUE 'T6091XA'.
               10  FILLER           PIC X(7) VALUE 'T6092XA'.
               10  FILLER           PIC X(7) VALUE 'T6093XA'.
               10  FILLER           PIC X(7) VALUE 'T6094XA'.
               10  FILLER           PIC X(7) VALUE 'T63001A'.
               10  FILLER           PIC X(7) VALUE 'T63002A'.
               10  FILLER           PIC X(7) VALUE 'T63003A'.
               10  FILLER           PIC X(7) VALUE 'T63004A'.
               10  FILLER           PIC X(7) VALUE 'T63011A'.
               10  FILLER           PIC X(7) VALUE 'T63012A'.
               10  FILLER           PIC X(7) VALUE 'T63013A'.
               10  FILLER           PIC X(7) VALUE 'T63014A'.
               10  FILLER           PIC X(7) VALUE 'T63021A'.
               10  FILLER           PIC X(7) VALUE 'T63022A'.
               10  FILLER           PIC X(7) VALUE 'T63023A'.
               10  FILLER           PIC X(7) VALUE 'T63024A'.
               10  FILLER           PIC X(7) VALUE 'T63031A'.
               10  FILLER           PIC X(7) VALUE 'T63032A'.
               10  FILLER           PIC X(7) VALUE 'T63033A'.
               10  FILLER           PIC X(7) VALUE 'T63034A'.
               10  FILLER           PIC X(7) VALUE 'T63041A'.
               10  FILLER           PIC X(7) VALUE 'T63042A'.
               10  FILLER           PIC X(7) VALUE 'T63043A'.
               10  FILLER           PIC X(7) VALUE 'T63044A'.
               10  FILLER           PIC X(7) VALUE 'T63061A'.
               10  FILLER           PIC X(7) VALUE 'T63062A'.
               10  FILLER           PIC X(7) VALUE 'T63063A'.
               10  FILLER           PIC X(7) VALUE 'T63064A'.
               10  FILLER           PIC X(7) VALUE 'T63071A'.
               10  FILLER           PIC X(7) VALUE 'T63072A'.
               10  FILLER           PIC X(7) VALUE 'T63073A'.
               10  FILLER           PIC X(7) VALUE 'T63074A'.
               10  FILLER           PIC X(7) VALUE 'T63081A'.
               10  FILLER           PIC X(7) VALUE 'T63082A'.
               10  FILLER           PIC X(7) VALUE 'T63083A'.
               10  FILLER           PIC X(7) VALUE 'T63084A'.
               10  FILLER           PIC X(7) VALUE 'T63091A'.
               10  FILLER           PIC X(7) VALUE 'T63092A'.
               10  FILLER           PIC X(7) VALUE 'T63093A'.
               10  FILLER           PIC X(7) VALUE 'T63094A'.
               10  FILLER           PIC X(7) VALUE 'T63111A'.
               10  FILLER           PIC X(7) VALUE 'T63112A'.
               10  FILLER           PIC X(7) VALUE 'T63113A'.
               10  FILLER           PIC X(7) VALUE 'T63114A'.
               10  FILLER           PIC X(7) VALUE 'T63121A'.
               10  FILLER           PIC X(7) VALUE 'T63122A'.
               10  FILLER           PIC X(7) VALUE 'T63123A'.
               10  FILLER           PIC X(7) VALUE 'T63124A'.
               10  FILLER           PIC X(7) VALUE 'T63191A'.
               10  FILLER           PIC X(7) VALUE 'T63192A'.
               10  FILLER           PIC X(7) VALUE 'T63193A'.
               10  FILLER           PIC X(7) VALUE 'T63194A'.
               10  FILLER           PIC X(7) VALUE 'T632X1A'.
               10  FILLER           PIC X(7) VALUE 'T632X2A'.
               10  FILLER           PIC X(7) VALUE 'T632X3A'.
               10  FILLER           PIC X(7) VALUE 'T632X4A'.
               10  FILLER           PIC X(7) VALUE 'T63301A'.
               10  FILLER           PIC X(7) VALUE 'T63302A'.
               10  FILLER           PIC X(7) VALUE 'T63303A'.
               10  FILLER           PIC X(7) VALUE 'T63304A'.
               10  FILLER           PIC X(7) VALUE 'T63311A'.
               10  FILLER           PIC X(7) VALUE 'T63312A'.
               10  FILLER           PIC X(7) VALUE 'T63313A'.
               10  FILLER           PIC X(7) VALUE 'T63314A'.
               10  FILLER           PIC X(7) VALUE 'T63321A'.
               10  FILLER           PIC X(7) VALUE 'T63322A'.
               10  FILLER           PIC X(7) VALUE 'T63323A'.
               10  FILLER           PIC X(7) VALUE 'T63324A'.
               10  FILLER           PIC X(7) VALUE 'T63331A'.
               10  FILLER           PIC X(7) VALUE 'T63332A'.
               10  FILLER           PIC X(7) VALUE 'T63333A'.
               10  FILLER           PIC X(7) VALUE 'T63334A'.
               10  FILLER           PIC X(7) VALUE 'T63391A'.
               10  FILLER           PIC X(7) VALUE 'T63392A'.
               10  FILLER           PIC X(7) VALUE 'T63393A'.
               10  FILLER           PIC X(7) VALUE 'T63394A'.
               10  FILLER           PIC X(7) VALUE 'T63411A'.
               10  FILLER           PIC X(7) VALUE 'T63412A'.
               10  FILLER           PIC X(7) VALUE 'T63413A'.
               10  FILLER           PIC X(7) VALUE 'T63414A'.
               10  FILLER           PIC X(7) VALUE 'T63421A'.
               10  FILLER           PIC X(7) VALUE 'T63422A'.
               10  FILLER           PIC X(7) VALUE 'T63423A'.
               10  FILLER           PIC X(7) VALUE 'T63424A'.
               10  FILLER           PIC X(7) VALUE 'T63431A'.
               10  FILLER           PIC X(7) VALUE 'T63432A'.
               10  FILLER           PIC X(7) VALUE 'T63433A'.
               10  FILLER           PIC X(7) VALUE 'T63434A'.
               10  FILLER           PIC X(7) VALUE 'T63441A'.
               10  FILLER           PIC X(7) VALUE 'T63442A'.
               10  FILLER           PIC X(7) VALUE 'T63443A'.
               10  FILLER           PIC X(7) VALUE 'T63444A'.
               10  FILLER           PIC X(7) VALUE 'T63451A'.
               10  FILLER           PIC X(7) VALUE 'T63452A'.
               10  FILLER           PIC X(7) VALUE 'T63453A'.
               10  FILLER           PIC X(7) VALUE 'T63454A'.
               10  FILLER           PIC X(7) VALUE 'T63461A'.
               10  FILLER           PIC X(7) VALUE 'T63462A'.
               10  FILLER           PIC X(7) VALUE 'T63463A'.
               10  FILLER           PIC X(7) VALUE 'T63464A'.
               10  FILLER           PIC X(7) VALUE 'T63481A'.
               10  FILLER           PIC X(7) VALUE 'T63482A'.
               10  FILLER           PIC X(7) VALUE 'T63483A'.
               10  FILLER           PIC X(7) VALUE 'T63484A'.
               10  FILLER           PIC X(7) VALUE 'T63511A'.
               10  FILLER           PIC X(7) VALUE 'T63512A'.
               10  FILLER           PIC X(7) VALUE 'T63513A'.
               10  FILLER           PIC X(7) VALUE 'T63514A'.
               10  FILLER           PIC X(7) VALUE 'T63591A'.
               10  FILLER           PIC X(7) VALUE 'T63592A'.
               10  FILLER           PIC X(7) VALUE 'T63593A'.
               10  FILLER           PIC X(7) VALUE 'T63594A'.
               10  FILLER           PIC X(7) VALUE 'T63611A'.
               10  FILLER           PIC X(7) VALUE 'T63612A'.
               10  FILLER           PIC X(7) VALUE 'T63613A'.
               10  FILLER           PIC X(7) VALUE 'T63614A'.
               10  FILLER           PIC X(7) VALUE 'T63621A'.
               10  FILLER           PIC X(7) VALUE 'T63622A'.
               10  FILLER           PIC X(7) VALUE 'T63623A'.
               10  FILLER           PIC X(7) VALUE 'T63624A'.
               10  FILLER           PIC X(7) VALUE 'T63631A'.
               10  FILLER           PIC X(7) VALUE 'T63632A'.
               10  FILLER           PIC X(7) VALUE 'T63633A'.
               10  FILLER           PIC X(7) VALUE 'T63634A'.
               10  FILLER           PIC X(7) VALUE 'T63691A'.
               10  FILLER           PIC X(7) VALUE 'T63692A'.
               10  FILLER           PIC X(7) VALUE 'T63693A'.
               10  FILLER           PIC X(7) VALUE 'T63694A'.
               10  FILLER           PIC X(7) VALUE 'T63711A'.
               10  FILLER           PIC X(7) VALUE 'T63712A'.
               10  FILLER           PIC X(7) VALUE 'T63713A'.
               10  FILLER           PIC X(7) VALUE 'T63714A'.
               10  FILLER           PIC X(7) VALUE 'T63791A'.
               10  FILLER           PIC X(7) VALUE 'T63792A'.
               10  FILLER           PIC X(7) VALUE 'T63793A'.
               10  FILLER           PIC X(7) VALUE 'T63794A'.
               10  FILLER           PIC X(7) VALUE 'T63811A'.
               10  FILLER           PIC X(7) VALUE 'T63812A'.
               10  FILLER           PIC X(7) VALUE 'T63813A'.
               10  FILLER           PIC X(7) VALUE 'T63814A'.
               10  FILLER           PIC X(7) VALUE 'T63821A'.
               10  FILLER           PIC X(7) VALUE 'T63822A'.
               10  FILLER           PIC X(7) VALUE 'T63823A'.
               10  FILLER           PIC X(7) VALUE 'T63824A'.
               10  FILLER           PIC X(7) VALUE 'T63831A'.
               10  FILLER           PIC X(7) VALUE 'T63832A'.
               10  FILLER           PIC X(7) VALUE 'T63833A'.
               10  FILLER           PIC X(7) VALUE 'T63834A'.
               10  FILLER           PIC X(7) VALUE 'T63891A'.
               10  FILLER           PIC X(7) VALUE 'T63892A'.
               10  FILLER           PIC X(7) VALUE 'T63893A'.
               10  FILLER           PIC X(7) VALUE 'T63894A'.
               10  FILLER           PIC X(7) VALUE 'T6391XA'.
               10  FILLER           PIC X(7) VALUE 'T6392XA'.
               10  FILLER           PIC X(7) VALUE 'T6393XA'.
               10  FILLER           PIC X(7) VALUE 'T6394XA'.
               10  FILLER           PIC X(7) VALUE 'T6401XA'.
               10  FILLER           PIC X(7) VALUE 'T6402XA'.
               10  FILLER           PIC X(7) VALUE 'T6403XA'.
               10  FILLER           PIC X(7) VALUE 'T6404XA'.
               10  FILLER           PIC X(7) VALUE 'T6481XA'.
               10  FILLER           PIC X(7) VALUE 'T6482XA'.
               10  FILLER           PIC X(7) VALUE 'T6483XA'.
               10  FILLER           PIC X(7) VALUE 'T6484XA'.
               10  FILLER           PIC X(7) VALUE 'T650X1A'.
               10  FILLER           PIC X(7) VALUE 'T650X2A'.
               10  FILLER           PIC X(7) VALUE 'T650X3A'.
               10  FILLER           PIC X(7) VALUE 'T650X4A'.
               10  FILLER           PIC X(7) VALUE 'T651X1A'.
               10  FILLER           PIC X(7) VALUE 'T651X2A'.
               10  FILLER           PIC X(7) VALUE 'T651X3A'.
               10  FILLER           PIC X(7) VALUE 'T651X4A'.
           05  FILLER     REDEFINES CAT17-TABLE-DATA.
               10  CAT17-DATA       OCCURS 464 TIMES
                                    ASCENDING KEY IS CAT17-CODE
                                    INDEXED BY IX-CAT17.
                   15  CAT17-CODE   PIC X(7).

