#version 120

uniform float iTime;
uniform sampler2D iChannel1;

varying vec3 vPosition;

#define PI 3.1415926538

vec2 quantize(vec2 inp, vec2 period) {
	return floor(inp / period) * period;
}

void main() {
	vec2 fragCoord = quantize(gl_TexCoord[0].xy, vec2(0.0625, 0.0625));
    float angle = iTime;
    vec2 uv = (2.25 * fragCoord - 1.1);
    vec2 suv = (2.0 * fragCoord - 1.0);
    
    vec3 light = vec3(sin(angle * PI), 0.0, cos(angle * PI));
	
    vec3 n = vec3(uv, sqrt(1.0 - clamp(dot(uv, uv), 0.0, 1.0)));
    float brightness = dot(n, light);
    
    // when nearly new moon, ring glow
    brightness = max(brightness, (abs(angle) - 0.7) * clamp(dot(suv, suv), 0.0, 1.0));
    
    // become full square when nearing full illumination
    if (abs(angle) < 0.5) {
        if (angle < 0.0) {
            brightness = angle * 4.0 + 2.0 - uv.x;
        } else {
            brightness = -angle * 4.0 + 2.0 + uv.x;
        }
    }
    
    // minimum brightness
    brightness = max(brightness, 0.05);

	gl_FragColor = vec4(0.0, 0.0, 0.0, 1.0 - brightness);
}
