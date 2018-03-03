@echo off 
rem 关闭自动输出

set foldername=
set /p foldername=Folder name:

md %foldername%

echo.
pause