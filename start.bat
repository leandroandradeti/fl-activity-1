@echo off
setlocal

REM Inicia o backend Spring Boot
REM - Executa scripts/setup-maven.ps1 se o mvn não estiver disponível
REM - Rodar em: ./backend

cd /d "%~dp0"

where mvn >nul 2>nul
if %errorlevel% neq 0 (
  echo Maven nao encontrado. Tentando instalar/ajustar via scripts/setup-maven.ps1 ...
  powershell -NoProfile -ExecutionPolicy Bypass -File .\scripts\setup-maven.ps1
)

cd /d .\backend
echo Iniciando Spring Boot...
mvn spring-boot:run

endlocal