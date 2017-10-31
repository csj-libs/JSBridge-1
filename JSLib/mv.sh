mkdir -p ../jstraw/src/main/assets
mkdir -p ../jstraw-ext/src/main/assets

cp -u ./dist/straw.bundle-$1.js ../jstraw/src/main/assets/
cp -u ./dist/straw_ext.bundle-$1.js ../jstraw-ext/src/main/assets/
