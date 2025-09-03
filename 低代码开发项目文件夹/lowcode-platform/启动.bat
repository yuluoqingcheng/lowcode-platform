@echo off
echo 🚀 启动低代码开发平台...
echo.

echo 📦 安装依赖中...
cd frontend
call npm install

echo.
echo 🔥 启动开发服务器...
call npm run dev

pause