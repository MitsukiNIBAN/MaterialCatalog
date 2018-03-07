@echo off
:addbegin

rem ��������
set name=
set /p name=Name:
if "%name%"=="" (
	goto addend
)
set name=%name: =%
if "%name%"=="" (
	goto addend
)

rem ���뼾
set season=
set /p season=Season:
if "%season%"=="" (
	goto addend
)
set season=%season: =%
if "%season%"=="" (
	goto addend
)

rem ���뼯
set episode=
set /p episode=Episode��
if "%episode%"=="" (
	goto addend
)
set episode=%episode: =%
if "%episode%"=="" (
	goto addend
)

rem ����ʱ
set time=
set /p time=Time��
if "%time%"=="" (
	goto addend
)
set time=%time: =%
if "%time%"=="" (
	goto addend
)

rem �������
:typebegin

set type=
set /p type=Type:

rem �������ݼ��
if "%type%"=="" (
	goto typeend
)
set type=%type: =%
if "%type%"=="" (
	goto typeend
)

rem �ļ��м��
if not exist %type% md %type%
rem echo %type%
rem ��ֹ�����ظ�����
for /f %%i in (folder_name_cache.txt) do (
	rem	echo д����Ϣ >> %%i\%scene%.txt
	if "%type%"=="%%i" (
		goto typebegin
	) 
)
echo %type%>>folder_name_cache.txt
goto typebegin
:typeend

rem ���볡��
:scenebegin

set scene=
set /p scene=Scene:

rem �������ݼ��
if "%scene%"=="" (
	goto sceneend
)
set scene=%scene: =%
if "%scene%"=="" (
	goto sceneend
)

rem �ظ����ݼ��
for /f %%i in (file_name_cache.txt) do (
	rem	echo д����Ϣ >> %%i\%scene%.txt
	if "%scene%"=="%%i" (
		goto scenebegin
	) 
)
rem �����ļ�д������
for /f %%i in (folder_name_cache.txt) do (
	rem	echo д����Ϣ >> %%i\%scene%.txt
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

rem ����ļ�����
echo.>folder_name_cache.txt
echo.>file_name_cache.txt

:addend
echo.
pause