@echo off
setlocal enabledelayedexpansion
set b=%cd%
echo ɾ�����ļ�
echo.

rd/s/q H:\IdeaProjects\rojegame\game-message\src\main\java\com

echo ��ʼ����proto����...
echo.

FOR %%p in (*.proto) do (����
	set proto=!proto!%%p 
)

echo %proto%
protoc --java_out H:\IdeaProjects\rojegame\game-message\src\main\java ./%proto%

::����protobuf��game-manage��̨
::xcopy *.proto G:\project_IEDA\game-server-master\game-manage\src\main\webapp\assets\proto /s /h

echo.
echo ִ�����...
echo.
PAUSE 