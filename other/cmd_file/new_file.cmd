@echo off 
rem �ر��Զ����

rem ��������
set filename=
set /p filename=File name:
set content=
set /p content=File content:

rem ����õ���������Ϣ
echo %content% >> %filename%.txt

echo.
pause
