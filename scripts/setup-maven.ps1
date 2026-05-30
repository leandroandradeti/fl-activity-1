param(
  [string]$MavenVersion = "3.9.6",
  [string]$MavenBaseUrl = "https://archive.apache.org/dist/maven/maven-3",
  [string]$InstallDir = "$env:USERPROFILE\C\Maven"
)

function Test-CommandExists([string]$cmd) {
  try {
    $null = Get-Command $cmd -ErrorAction Stop
    return $true
  } catch {
    return $false
  }
}

function Get-MavenExePath($dir) {
  $pattern = Join-Path $dir ("apache-maven-*")
  $m = Get-ChildItem -Path $dir -Directory -ErrorAction SilentlyContinue | Where-Object { $_.Name -like "apache-maven-*" } | Select-Object -First 1
  if ($null -eq $m) { return $null }
  $exe = Join-Path $m.FullName "bin\mvn.cmd"
  if (Test-Path $exe) { return $exe }
  return $null
}

Write-Host "Verificando Maven (mvn) no PATH..."

if (Test-CommandExists "mvn") {
  Write-Host "Maven já está instalado e disponível no PATH. mvn -v:"
  & mvn -v
  exit 0
}

Write-Host "Maven não encontrado. Instalando localmente em: $InstallDir"

# Download
New-Item -ItemType Directory -Force -Path $InstallDir | Out-Null

$zipName = "apache-maven-$MavenVersion-bin.zip"
$url = "$MavenBaseUrl/$MavenVersion/binaries/$zipName"

Write-Host "Baixando: $url"
$tempZip = Join-Path $env:TEMP $zipName

try {
  Invoke-WebRequest -Uri $url -OutFile $tempZip -UseBasicParsing
} catch {
  Write-Host "Falha ao baixar Maven. Erro: $_"
  exit 1
}

# Extract
Add-Type -AssemblyName System.IO.Compression.FileSystem
$extractTo = Join-Path $InstallDir "apache-maven-$MavenVersion"
if (Test-Path $extractTo) {
  Write-Host "Removendo instalação anterior em: $extractTo"
  Remove-Item -Recurse -Force $extractTo
}
New-Item -ItemType Directory -Force -Path $extractTo | Out-Null

Write-Host "Extraindo..."
[System.IO.Compression.ZipFile]::ExtractToDirectory($tempZip, $extractTo)

# PATH: precisa apontar para <InstallDir>\apache-maven-<ver>\bin
$binDir = Join-Path $extractTo "bin"
if (!(Test-Path $binDir)) {
  Write-Host "Diretório bin não encontrado: $binDir"
  exit 1
}

# Adiciona PATH permanentemente (setx)
Write-Host "Adicionando ao PATH (permanente) via setx: $binDir"
$existing = [Environment]::GetEnvironmentVariable("Path", "User")
if ($existing -notmatch [regex]::Escape($binDir)) {
  $newPath = "$existing;$binDir"
  setx Path $newPath | Out-Null
}

Write-Host "Feche e reabra o terminal para o PATH atualizado ficar ativo."
Write-Host "Tentando executar mvn -v agora..."

# Tenta executar mvn usando mvn.cmd direto do binDir (mesmo sem novo terminal)
$mvnCmd = Join-Path $binDir "mvn.cmd"
if (Test-Path $mvnCmd) {
  & $mvnCmd -v
  exit 0
} else {
  Write-Host "Não foi possível encontrar mvn.cmd em: $mvnCmd"
  exit 1
}
