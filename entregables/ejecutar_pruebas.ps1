$ErrorActionPreference = 'Stop'
$proyectoBiblioteca = Split-Path -Parent $PSScriptRoot
$salidaBiblioteca = Join-Path ([System.IO.Path]::GetTempPath()) ('biblioteca-pruebas-' + [guid]::NewGuid())
New-Item -ItemType Directory -Path $salidaBiblioteca | Out-Null
$fuentesBiblioteca = (Get-ChildItem -LiteralPath (Join-Path $proyectoBiblioteca 'src/biblioteca') -Filter '*.java').FullName
& javac -encoding UTF-8 -d $salidaBiblioteca $fuentesBiblioteca (Join-Path $PSScriptRoot 'pruebas/MainPruebas.java')
if ($LASTEXITCODE -ne 0) { throw 'Falló la compilación.' }
& java -cp $salidaBiblioteca biblioteca.MainPruebas | Tee-Object -FilePath (Join-Path $PSScriptRoot 'evidencias/resultados.txt')
if ($LASTEXITCODE -ne 0) { throw 'Falló una prueba.' }
