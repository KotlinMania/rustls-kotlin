// port-lint: source time_provider.rs
package io.github.kotlinmania.rustls

import io.github.kotlinmania.rustls.pki.UnixTime

/**
 * An object that provides the current time.
 *
 * This is used to, for example, check if a certificate has expired during
 * certificate validation, or to check the age of a ticket.
 */
interface TimeProvider {
    /**
     * Returns the current wall time.
     *
     * This is not required to be monotonic.
     *
     * Return null if unable to retrieve the time.
     */
    fun currentTime(): UnixTime?
}

/**
 * Default [TimeProvider] implementation.
 */
object DefaultTimeProvider : TimeProvider {
    override fun currentTime(): UnixTime = UnixTime.now()
}
