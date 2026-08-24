# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 0/107 (0.0%)
- **Function parity:** 0/2038 matched — 0.0%
- **Class/type parity:** 0/487 matched — 0.0%
- **Combined symbol parity:** 0/2525 matched — 0.0%
- **Average inline-code cosine:** 0.00 (function body across 0 matched files)
- **Average documentation cosine:** 0.00 (doc text across 0 matched files)
- **Cheat-zeroed Files:** 0
- **Critical Issues:** 0 files with <0.60 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

No incomplete high-dependency files detected.

## Priority 2: Port Missing High-Value Files

Critical missing files (>10 dependencies):

1. **error** (27 deps)
   - Path: `error.rs`
   - Essential for 27 other files

## Detailed Work Items

Every matched file is listed below with function and type symbol parity.

## Success Criteria

For each file to be considered "complete":
- **Similarity ≥ 0.85** (Excellent threshold)
- All public APIs ported
- All tests ported
- Documentation ported
- port-lint header present

## Reexport / Wiring Modules

These files match `reexport_modules` patterns in `.ast_distance_config.json`. They are filtered out of
normal priority and missing-file ladders because they are wiring
modules, not direct logic ports. Consult them for call-site routing;
do not treat them as the next implementation target by default.

### Missing

| Source | Expected target | Deps | Source path | Expected path |
|--------|-----------------|------|-------------|---------------|
| `aws_lc_rs.mod` | `crypto.awslcrs.Mod` | 0 | `crypto/aws_lc_rs/mod.rs` | `crypto/awslcrs/Mod.kt` |
| `pq.mod` | `crypto.awslcrs.pq.Mod` | 0 | `crypto/aws_lc_rs/pq/mod.rs` | `crypto/awslcrs/pq/Mod.kt` |
| `crypto.mod` | `crypto.Mod` | 0 | `crypto/mod.rs` | `crypto/Mod.kt` |
| `ring.mod` | `crypto.ring.Mod` | 0 | `crypto/ring/mod.rs` | `crypto/ring/Mod.kt` |
| `lib` | `Lib` | 0 | `lib.rs` | `Lib.kt` |
| `manual.mod` | `manual.Mod` | 0 | `manual/mod.rs` | `manual/Mod.kt` |
| `deframer.mod` | `msgs.deframer.Mod` | 0 | `msgs/deframer/mod.rs` | `msgs/deframer/Mod.kt` |
| `message.mod` | `msgs.message.Mod` | 0 | `msgs/message/mod.rs` | `msgs/message/Mod.kt` |
| `msgs.mod` | `msgs.Mod` | 0 | `msgs/mod.rs` | `msgs/Mod.kt` |
| `tls12.mod` | `tls12.Mod` | 0 | `tls12/mod.rs` | `tls12/Mod.kt` |
| `tls13.mod` | `tls13.Mod` | 0 | `tls13/mod.rs` | `tls13/Mod.kt` |
| `webpki.mod` | `webpki.Mod` | 0 | `webpki/mod.rs` | `webpki/Mod.kt` |

