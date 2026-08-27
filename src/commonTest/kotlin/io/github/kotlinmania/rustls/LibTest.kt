// port-lint: tests rustls/benches/benchmarks.rs
package io.github.kotlinmania.rustls

import kotlin.test.Test
import kotlin.test.assertEquals

class LibTest {
    @Test
    fun testRustlsVersion() {
        assertEquals("0.23.36", Rustls.VERSION)
    }
}
