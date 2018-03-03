@echo off
:addbegin

rem 输入名字
set name=
set /p name=Name:
if "%name%"=="" (
	goto addend
)
set name=%name: =%
if "%name%"=="" (
	goto addend
)

rem 输入季
set season=
set /p season=Season:
if "%season%"=="" (
	goto addend
)
set season=%season: =%
if "%season%"=="" (
	goto addend
)

rem 输入集
set episode=
set /p episode=Episode：
if "%episode%"=="" (
	goto addend
)
set episode=%episode: =%
if "%episode%"=="" (
	goto addend
)

rem 输入时
set time=
set /p time=Time：
if "%time%"=="" (
	goto addend
)
set time=%time: =%
if "%time%"=="" (
	goto addend
)

rem 输入类别
:typebegin

set type=
set /p type=Type:

rem 输入内容检查
if "%type%"=="" (
	goto typeend
)
set type=%type: =%
if "%type%"=="" (
	goto typeend
)

rem 文件夹检查
if not exist %type% md %type%
rem echo %type%
rem 防止输入重复内容
for /f %%i in (folder_name_cache.txt) do (
	rem	echo 写入信息 >> %%i\%scene%.txt
	if "%type%"=="%%i" (
		goto typebegin
	) 
)
echo %type%>>folder_name_cache.txt
goto typebegin
:typeend

rem 输入场景
:scenebegin

set scene=
set /p scene=Scene:

rem 输入内容检查
if "%scene%"=="" (
	goto sceneend
)
set scene=%scene: =%
if "%scene%"=="" (
	goto sceneend
)

rem 重复内容检查
for /f %%i in (file_name_cache.txt) do (
	rem	echo 写入信息 >> %%i\%scene%.txt
	if "%scene%"=="%%i" (
		goto scenebegin
	) 
)
rem 创建文件写入数据
for /f %%i in (folder_name_cache.txt) do (
	rem	echo 写入信息 >> %%i\%scene%.txt
	if exist "%%i\%scene%.md" (
		echo ^|%name%^|%season%^|%episode%^|%time%^|>>%%i\%scene%.md
	) else (
		echo ^|Name^|Episode^|Season^|Content^|>>%%i\%scene%.md
		echo ^|:-^|:-^|:-^|:-^|>>%%i\%scene%.md
		echo ^|%name%^|%season%^|%episode%^|%time%^|>>%%i\%scene%.md
	)
)
echo %scene%>>file_name_cache.txt
goto scenebegin
:sceneend

rem 清除文件缓存
echo.>folder_name_cache.txt
echo.>file_name_cache.txt

:addend
echo.
pause