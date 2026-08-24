import Testing
import Rustls

@Suite("Rustls Swift Export Smoke Tests")
struct RustlsExportTests {
    @Test("Rustls swift module imports cleanly")
    func swiftModuleLoads() {
        #expect(true)
    }

    @Test("Rustls exported types instantiate cleanly")
    func exportedTypesInstantiate() {
        let alert = AlertDescription.companion.CloseNotify
        #expect(alert.value == 0)

        let handshake = HandshakeType.companion.ClientHello
        #expect(handshake.value == 1)

        let contentType = ContentType.companion.Handshake
        #expect(contentType.value == 0x16)

        let version = ProtocolVersion.companion.TLSv1_3
        #expect(version.value == 0x0304)

        let scheme = SignatureScheme.companion.ED25519
        #expect(scheme.supportedInTls13() == true)

        let payload = Payload.companion.empty()
        #expect(payload.intoVec().isEmpty)
    }
}
