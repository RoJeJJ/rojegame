@echo off
setlocal enabledelayedexpansion
set b=%cd%
echo ɾ�����ļ�
echo.

rd/s/q G:\project_IEDA\rojegame\game-message\src\main\java\com

echo ��ʼ����proto����...
echo.

FOR %%p in (*.proto) do (
	set proto=!proto!%%p 
)

echo %proto%
protoc --java_out G:\project_IEDA\rojegame\game-message\src\main\java ./%proto%


echo.
echo ִ�����...
echo.
PAUSE 