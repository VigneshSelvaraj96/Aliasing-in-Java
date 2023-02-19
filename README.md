# Aliasing-in-Java
Aliasing effects shown and treated (only for static image not for video) in Java
The first program shows aliasing effects on the right wheel (constructed) while the left wheel is the original input.  Input to your program will take three parameters: • The first parameter n is the number of lines to create an image with radial pattern of n black lines starting from the center of the image towards the boundaries. The image has a white background. Each consecutive line is separated by 360/n degrees. The idea here is by increasing n, you can increase the frequency content in an image. • The second parameter s will be scaling value that scales the input image by a factor. This is a floating-point number eg – s=0.5 will scale the image down to 256x256. Note s will be a floating-point number between 0 and 1.0. • The third parameter will be a Boolean value (0 or 1) suggesting whether or not you want to deal with aliasing. A 0 signifies do nothing (your output will have aliasing) – which means you need copy the direct mapped pixel value from input to output. A value 1 signifies that anti-aliasing should be performed – which means that instead of the direct mapped value you need to copy a low pass filtered value to the output.  



Your original video displayed on the left – This is video of size 512x512 that you will create based on the criteria explained below. This is radial pattern just as in part 1, but it is also rotating clockwise at a certain specified speed. The creation and updating of your image at the respective times should simulate a rotating wheel. 2. Your processed output video displayed on the right – The output video is also of size 512x512 but in order to simulate temporal aliasing effects it will be given an fps rate of display, which means your output will be updated at specific times. Input to your program will take three parameters where • The first parameter n is the number of lines to create an image with radial pattern of n black lines starting from the center of the image towards the boundaries. The image has a white background. Each consecutive line is separated by 360/n degrees. The idea here is by increasing n, you can increase the frequency content in an image. • The second parameter s will be a speed of rotations in terms of rotations per second. This is a floating-point number eg – s=2.0 indicates that the wheel is making two full rotations in a second, s=7.5 indicates that the wheel is making seven and a half rotations in a second. Remember this is the original input video signal with a very high display rate. • The third parameter will be a fps value suggesting that not all frames of the input video are displayed, but only a specific frames per second are displayed. 
