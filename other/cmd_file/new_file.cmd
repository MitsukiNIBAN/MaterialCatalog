@echo off 
rem 关闭自动输出

rem 接收输入
set filename=
set /p filename=File name:
set content=
set /p content=File content:

rem 输出得到的输入信息
echo %content% >> %filename%.txt

echo.
pause
