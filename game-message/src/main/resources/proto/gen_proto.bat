@echo off
setlocal enabledelayedexpansion
set b=%cd%
echo 删除旧文件
echo.

rd/s/q G:\project_IEDA\rojegame\game-message\src\main\java\com

echo 开始生成proto代码...
echo.

FOR %%p in (*.proto) do (
	set proto=!proto!%%p 
)

echo %proto%
protoc --java_out G:\project_IEDA\rojegame\game-message\src\main\java ./%proto%


echo.
echo 执行完成...
echo.
PAUSE 