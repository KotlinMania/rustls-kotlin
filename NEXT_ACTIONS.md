# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 10/107 (9.3%)
- **Function parity:** 34/2095 matched (target 246) — 1.6%
- **Class/type parity:** 27/512 matched (target 205) — 5.3%
- **Combined symbol parity:** 61/2607 matched (target 451) — 2.3%
- **Average inline-code cosine:** 0.33 (function body across 9 matched files)
- **Average documentation cosine:** 0.60 (doc text across 9 matched files)
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

- **Target:** `rustls.Lib [ZERO]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 20210.0
- **Functions:** 0/0 matched (target 67)
- **Missing functions:** _none_
- **Types:** 0/2 matched (target 32)
- **Missing types:** `Arc`, `Weak`

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

