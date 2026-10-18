Remove-Item -Path "generated-proto" -Recurse -ErrorAction SilentlyContinue
New-Item -ItemType Directory -Force -Path "generated-proto" | Out-Null
New-Item -ItemType Directory -Force -Path "target\classes" | Out-Null

$protoc = (Resolve-Path "target\protoc-plugins\protoc-3.25.5-windows-x86_64.exe").Path
$grpcPlugin = (Resolve-Path "target\protoc-plugins\protoc-gen-grpc-java-1.68.1-windows-x86_64.exe").Path
$depList = (Get-ChildItem -Path "target\protoc-dependencies" -Directory).FullName

& $protoc --proto_path=src\main\proto --proto_path=$depList[0] --proto_path=$depList[1] --java_out=generated-proto --plugin=protoc-gen-grpc-java=$grpcPlugin --grpc-java_out=generated-proto src\main\proto\memory\v1\memory_service.proto

mvn dependency:build-classpath -Dmdep.outputFile=target/cp.txt | Out-Null

$sourcesRaw = (Get-ChildItem -Path "generated-proto" -Recurse -Filter "*.java").FullName
$sources = [System.Collections.Generic.List[string]]::new()
foreach ($s in $sourcesRaw) {
    $sources.Add('"' + $s.Replace('\', '/') + '"')
}

$cp = (Get-Content "target\cp.txt" -Raw).Trim().Replace('\', '/')
$outDir = ($PWD.Path + "/target/classes").Replace('\', '/')

$lines = [System.Collections.Generic.List[string]]::new()
$lines.Add("-cp")
$lines.Add('"' + $cp + '"')
$lines.Add("-d")
$lines.Add('"' + $outDir + '"')
foreach ($src in $sources) {
    $lines.Add($src)
}

[System.IO.File]::WriteAllLines(($PWD.Path + "/target/compile_direct.txt"), $lines)
& "$env:JAVA_HOME\bin\javac.exe" "@target/compile_direct.txt"

mvn surefire:test "-Dtest=MetadataEnrichmentProcessTest"