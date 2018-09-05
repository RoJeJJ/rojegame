@echo off
setlocal enabledelayedexpansion

echo 开始生成proto代码...
echo.

FOR %%p in (*.proto) do (　　
	set proto=!proto!%%p 
)

echo %proto%
protoc --java_out G:\project_IEDA\rojegame\game-message\src\main\java ./%proto%

::复制protobuf到game-manage后台
::xcopy *.proto G:\project_IEDA\game-server-master\game-manage\src\main\webapp\assets\proto /s /h

echo.
echo 执行完成...
echo.
PAUSE 