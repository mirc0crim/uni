#version 150
// GLSL version 1.50
// Fragment shader for brick shading

// Variables passed in from the vertex shader
in vec2 MCposition;
in float LightIntensity;

// Output variable, will be written to framebuffer automatically
out vec4 frag_shaded;

void main() {

	vec3 BrickColor = vec3(1.0,0.3,0.2);
	vec3 MortarColor = vec3(0.85,0.86,0.84);
	vec2 BrickSize = vec2(0.3,0.15);
	vec2 BrickPct = vec2(0.9,0.85);

	vec3 color;
    vec2 position, useBrick;
    position = MCposition / BrickSize;

    if (fract(position.y * 0.5) > 0.5)
        position.x += 0.5;

    position = fract(position);

    useBrick = step(position, BrickPct);

    color  = mix(MortarColor, BrickColor, useBrick.x * useBrick.y);
    color *= LightIntensity;
    gl_FragColor = vec4(color, 1.0);
}
