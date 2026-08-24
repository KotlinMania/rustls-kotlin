import Testing
import Rustls

@Suite("Rustls Swift Export Smoke Tests")
struct RustlsExportTests {
    @Test("Rustls swift module imports cleanly")
    func swiftModuleLoads() {
        #expect(Bool(true))
    }

    @Test("Rustls exported types instantiate cleanly")
    func exportedTypesInstantiate() {
        let alert = AlertDescription.Companion.shared.CloseNotify
        #expect(alert.value == 0)

        let handshake = HandshakeType.Companion.shared.ClientHello
        #expect(handshake.value == 1)

        let contentType = ContentType.Companion.shared.Handshake
        #expect(contentType.value == 0x16)

        let version = ProtocolVersion.Companion.shared.TLSv1_3
        #expect(version.value == 0x0304)

        let scheme = SignatureScheme.Companion.shared.ED25519
        #expect(scheme.supportedInTls13() == true)

        let payload = msgs.Payload.Companion.shared.empty()
        #expect(payload.intoVec().size == 0)
    }
}
