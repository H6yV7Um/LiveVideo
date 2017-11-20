precision highp float;
 varying highp vec2 textureCoordinate;

 uniform sampler2D inputImageTexture;

 uniform lowp float thinparam;

 uniform lowp vec2 location0;
 uniform lowp vec2 location1;
 uniform lowp vec2 location2;
 uniform lowp vec2 location3;
 uniform lowp vec2 location4;
 uniform lowp vec2 location5;
 uniform lowp vec2 location6;
 uniform lowp vec2 location7;
 uniform highp float aspectRatio;


 vec2 faceStretch(vec2 textureCoord, vec2 originPosition, vec2 targetPosition, float radius, float curve)
{
    vec2 direction = targetPosition - originPosition;
    float lengthA = length(direction);
    float lengthB = min(lengthA, radius);
    direction *= lengthB / lengthA;
    float infect = distance(textureCoord, originPosition)/radius;
    infect = clamp(1.0-infect,0.0,1.0);
    infect = pow(infect, curve);

    return direction * infect;
}

 void main()
{

    vec2 p_faceleft= location0;
    vec2 p_chin =location1;
    vec2 p_faceright= location2;
    vec2 p_nose= location3;
    vec2 p_eyea =location4;
    vec2 p_eyeb =location5;
    vec2 p_chinleft =location6;
    vec2 p_chinright= location7;

    float x_a= 1.0;
    float y_a =aspectRatio;
    gl_FragColor = texture2D(inputImageTexture,textureCoordinate);

    vec2 newCoord = vec2(textureCoordinate.x*x_a,textureCoordinate.y*y_a);


    if(location3.x>0.03 && location3.y>0.03)
    {
        vec2 eyea = vec2(p_eyea.x * x_a, p_eyea.y * y_a);
        vec2 eyeb = vec2(p_eyeb.x * x_a, p_eyeb.y * y_a);

        vec2 faceleft = vec2(p_faceleft.x * x_a, p_faceleft.y * y_a);
        vec2 faceright = vec2(p_faceright.x * x_a, p_faceright.y * y_a);

        vec2 chinleft = vec2(p_chinleft.x * x_a, p_chinleft.y * y_a);
        vec2 chinright = vec2(p_chinright.x * x_a, p_chinright.y * y_a);

        vec2 nose = vec2(p_nose.x * x_a, p_nose.y * y_a);
        vec2 chin = vec2(p_chin.x * x_a, p_chin.y * y_a);

        vec2 chinCenter = nose + (chin - nose) * 0.7;


        float weight = 0.0;
        float face_width = distance(eyea,eyeb);


//        float para1 = 0.7;
        float radius = face_width*1.0;
        vec2 leftF = faceleft;
        vec2 targetleftF = nose + (leftF - nose) * thinparam;
        vec2 leftFplus = vec2(0.0);
        leftFplus = faceStretch(newCoord, leftF, targetleftF, radius, 1.0);
        newCoord = newCoord - leftFplus;

        vec2 rightF = faceright;
        vec2 targetrightF = nose + (rightF - nose) * thinparam;
        vec2 rightFplus = vec2(0.0);
        rightFplus = faceStretch(newCoord, rightF, targetrightF, radius, 1.0);
        newCoord = newCoord - rightFplus;


        float para2 = 0.98;
        radius = face_width*1.2;
        vec2 leftC = chinleft;
        vec2 targetleftC = chinCenter + (leftC - chinCenter) * para2;
        vec2 leftCplus = vec2(0.0);
        leftCplus = faceStretch(newCoord, leftC, targetleftC, radius, 1.0);
        newCoord = newCoord + leftCplus;

        vec2 rightC = chinright;
        vec2 targetrightC = chinCenter + (rightC - chinCenter) * para2;
        vec2 rightCplus = vec2(0.0);
        rightCplus = faceStretch(newCoord, rightC, targetrightC, radius, 1.0);
        newCoord = newCoord + rightCplus;


        newCoord = vec2(newCoord.x/x_a, newCoord.y/y_a);
        gl_FragColor = texture2D(inputImageTexture, newCoord);
    }
 }