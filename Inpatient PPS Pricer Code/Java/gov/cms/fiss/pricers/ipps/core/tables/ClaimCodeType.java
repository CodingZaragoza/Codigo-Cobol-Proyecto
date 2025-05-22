package gov.cms.fiss.pricers.ipps.core.tables;

/** Defines the code types supported for new technologies. */
public enum ClaimCodeType {
  /** Indicates the code is a condition code. */
  COND,
  /** Indicates the code is a diagnosis code. */
  DIAG,
  /** Indicates the code is a national drug code. */
  NDC,
  /** Indicates the code is a procedure code. */
  PROC;
}
