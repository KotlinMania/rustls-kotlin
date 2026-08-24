// port-lint: source msgs/alert.rs
package io.github.kotlinmania.rustls.msgs

import io.github.kotlinmania.rustls.AlertDescription

/**
 * An Alert message payload.
 */
data class AlertMessagePayload(
    val level: AlertLevel,
    val alertDescription: AlertDescription,
) {
    companion object : Codec<AlertMessagePayload> {
        override fun encode(value: AlertMessagePayload, output: OutputBuffer) {
            AlertLevel.encode(value.level, output)
            AlertDescription.encode(value.alertDescription, output)
        }

        override fun read(reader: Reader): AlertMessagePayload {
            val level = AlertLevel.read(reader)
            val description = AlertDescription.read(reader)
            reader.expectEmpty("AlertMessagePayload")
            return AlertMessagePayload(level, description)
        }
    }
}
