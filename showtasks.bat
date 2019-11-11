call runcrud
if "%ERRORLEVEL%" == "0" goto startchrome
echo.
echo GRADLEW BUILD has errors - breaking work
goto fail

:startchrome
start chrome "http://localhost:8080/crud/v1/task/getTasks"
if "%ERRORLEVEL%" == "0" goto end
echo.
echo Cannot open Chrome and show task list - breaking work

:fail
echo.
echo There were errors!

:end
echo.
echo Work is finished - site loaded http://localhost:8080/crud/v1/task/getTasks