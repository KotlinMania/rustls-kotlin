# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 10/107 (9.3%)
- **Function parity:** 34/2008 matched (target 246) — 1.7%
- **Class/type parity:** 27/496 matched (target 205) — 5.4%
- **Combined symbol parity:** 61/2504 matched (target 451) — 2.4%
- **Average inline-code cosine:** 0.37 (function body across 8 matched files)
- **Average documentation cosine:** 0.64 (doc text across 8 matched files)
- **Cheat-zeroed Files:** 3
- **Critical Issues:** 8 files with <0.60 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

### 1. error
- **Similarity:** 0.00 (needs 85% improvement)
- **Dependencies:** 27
- **Priority Score:** 27122210.0
- **Functions:** 0/12 matched (target 26)
- **Missing functions:** `from`, `eq`, `fmt`, `for_values`, `join`, `source`, `certificate_error_equality`, `crl_error_equality`, `other_error_equality`, `smoke`, `rand_error_mapping`, `time_error_mapping`
- **Types:** 10/10 matched (target 112)
- **Missing types:** _none_
- **Symbol Deficit:** 12 (functions: 12, types: 0)
- **Missing Tests:** 6 of 6 `#[test]` functions have no Kotlin counterpart
- **Action:** Deep review - likely missing major functionality

## Priority 2: Port Missing High-Value Files

Critical missing files (>10 dependencies):

No missing high-value files detected.

## Detailed Work Items

Every matched file is listed below with function and type symbol parity.

### 1. error

- **Target:** `rustls.Error`
- **Similarity:** 0.00
- **Dependents:** 27
- **Priority Score:** 27122210.0
- **Functions:** 0/12 matched (target 26)
- **Missing functions:** `from`, `eq`, `fmt`, `for_values`, `join`, `source`, `certificate_error_equality`, `crl_error_equality`, `other_error_equality`, `smoke`, `rand_error_mapping`, `time_error_mapping`
- **Types:** 10/10 matched (target 112)
- **Missing types:** _none_
- **Tests:** 0/6 matched

### 2. msgs.codec

- **Target:** `msgs.Codec`
- **Similarity:** 0.42
- **Dependents:** 7
- **Priority Score:** 7062706.0
- **Functions:** 15/19 matched (target 45)
- **Missing functions:** `from`, `new`, `next`, `drop`
- **Types:** 6/8 matched (target 17)
- **Missing types:** `TlsListIter`, `Item`
- **Tests:** 1/1 matched

### 3. crypto.hash

- **Target:** `crypto.Hash`
- **Similarity:** 0.61
- **Dependents:** 6
- **Priority Score:** 6000604.0
- **Functions:** 3/3 matched (target 6)
- **Missing functions:** _none_
- **Types:** 3/3 matched
- **Missing types:** _none_

### 4. time_provider

- **Target:** `rustls.TimeProvider`
- **Similarity:** 0.84
- **Dependents:** 4
- **Priority Score:** 4000301.5
- **Functions:** 1/1 matched
- **Missing functions:** _none_
- **Types:** 2/2 matched
- **Missing types:** _none_

### 5. crypto.mod

- **Target:** `crypto.Types [STUB]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 232410.0
- **Functions:** 0/17 matched (target 3)
- **Missing functions:** `install_default`, `get_default`, `get_default_or_install_from_crate_features`, `from_crate_features`, `fips`, `start_and_complete`, `ffdhe_group`, `usable_for_version`, `complete_for_tls_version`, `hybrid_component`, `complete_hybrid_component`, `secret_bytes`, `strip_leading_zeros`, `drop`, `from`, `default_fips_provider`, `test_shared_secret_strip_leading_zeros`
- **Types:** 1/7 matched (target 3)
- **Missing types:** `CryptoProvider`, `KeyProvider`, `SupportedKxGroup`, `ActiveKeyExchange`, `CompletedKeyExchange`, `SharedSecret`
- **Tests:** 0/1 matched

### 6. msgs.base

- **Target:** `msgs.Base`
- **Similarity:** 0.25
- **Dependents:** 0
- **Priority Score:** 91807.5
- **Functions:** 5/11 matched (target 26)
- **Missing functions:** `into_owned`, `new`, `fmt`, `encode_slice`, `zeroize`, `hex`
- **Types:** 4/7 matched (target 5)
- **Missing types:** `Cardinality`, `MaybeEmpty`, `NonEmpty`

### 7. msgs.enums

- **Target:** `msgs.Enums`
- **Similarity:** 0.27
- **Dependents:** 0
- **Priority Score:** 50907.3
- **Functions:** 4/9 matched (target 45)
- **Missing functions:** `tag_len`, `test_enum8`, `test_enum16`, `get8`, `get16`
- **Types:** 0/0 matched (target 19)
- **Missing types:** _none_
- **Tests:** 1/5 matched

### 8. lib

- **Target:** `pki.PkiTypes [STUB] [PROVENANCE-FALLBACK]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 20210.0
- **Functions:** 0/0 matched (target 67)
- **Missing functions:** _none_
- **Types:** 0/2 matched (target 32)
- **Missing types:** `Arc`, `Weak`
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/lib.rs` vs expected `lib.rs`
- **Proposed provenance header:** `// port-lint: source lib.rs` (current: `// port-lint: source src/lib.rs`)
- **Lint issues:** 1

### 9. enums

- **Target:** `rustls.Enums [ZERO]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 410.0
- **Functions:** 4/4 matched (target 25)
- **Missing functions:** _none_
- **Types:** 0/0 matched (target 11)
- **Missing types:** _none_
- **Tests:** 2/2 matched

### 10. msgs.alert

- **Target:** `msgs.Alert`
- **Similarity:** 0.57
- **Dependents:** 0
- **Priority Score:** 304.3
- **Functions:** 2/2 matched
- **Missing functions:** _none_
- **Types:** 1/1 matched
- **Missing types:** _none_

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
| `ring.mod` | `crypto.ring.Mod` | 0 | `crypto/ring/mod.rs` | `crypto/ring/Mod.kt` |
| `manual.mod` | `manual.Mod` | 0 | `manual/mod.rs` | `manual/Mod.kt` |
| `deframer.mod` | `msgs.deframer.Mod` | 0 | `msgs/deframer/mod.rs` | `msgs/deframer/Mod.kt` |
| `message.mod` | `msgs.message.Mod` | 0 | `msgs/message/mod.rs` | `msgs/message/Mod.kt` |
| `msgs.mod` | `msgs.Mod` | 0 | `msgs/mod.rs` | `msgs/Mod.kt` |
| `tls12.mod` | `tls12.Mod` | 0 | `tls12/mod.rs` | `tls12/Mod.kt` |
| `tls13.mod` | `tls13.Mod` | 0 | `tls13/mod.rs` | `tls13/Mod.kt` |
| `webpki.mod` | `webpki.Mod` | 0 | `webpki/mod.rs` | `webpki/Mod.kt` |

