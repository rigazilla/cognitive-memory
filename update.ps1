cognition-processor-quarkus/src/test/java/io/github/rigazilla/memory/cognition/justify/MemoryJustifyServiceTest.java = "cognition-processor-quarkus/src/test/java/io/github/rigazilla/memory/cognition/justify/MemoryJustifyServiceTest.java"
 = Get-Contentcognition-processor-quarkus/src/test/java/io/github/rigazilla/memory/cognition/justify/MemoryJustifyServiceTest.java -Raw

 = -replace 'package io.github.rigazilla.memory.cognition.justify;', "package io.github.rigazilla.memory.cognition.justify;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import jakarta.inject.Inject;"
 = -replace 'class MemoryJustifyServiceTest \{', "@QuarkusTest
class MemoryJustifyServiceTest {"
 =  -replace 'private MemoryJustifyService service;', '@Inject
    MemoryJustifyService service;'
 = -replace 'private AdminMemoriesServiceGrpc.AdminMemoriesServiceBlockingStub mockMemoriesStub;', '@InjectMock
    AdminMemoriesServiceGrpc.AdminMemoriesServiceBlockingStub mockMemoriesStub;'
 =  -replace 'private AdminEntriesServiceGrpc.AdminEntriesServiceBlockingStub mockEntriesStub;', '@InjectMock
    AdminEntriesServiceGrpc.AdminEntriesServiceBlockingStub mockEntriesStub;'
 = -replace 'service = new MemoryJustifyService\(\);', '// CDI-managed instances injected automatically'
 = -replace 'mockMemoriesStub = mock\(AdminMemoriesServiceGrpc.AdminMemoriesServiceBlockingStub\.class\);', ''
 = -replace 'mockEntriesStub = mock\(AdminEntriesServiceGrpc.AdminEntriesServiceBlockingStub\.class\);', ''

Set-Content cognition-processor-quarkus/src/test/java/io/github/rigazilla/memory/cognition/justify/MemoryJustifyServiceTest.java
