@echo off
REM set datestr = %date%
REM echo Date format %datestr:~0,2%
REM git log --since="5 days ago" --reverse > %userprofile%\Desktop\weeklyLog %datestr%.txt
REM echo Created weekly log in %userprofile%\Desktop

git log --since="5 days ago" --reverse > %userprofile%\Desktop\weeklyLog.txt
echo Created weekly log in %userprofile%\Desktop