#version 150
// GLSL version 1.50
// Fragment shader for diffuse shading in combination with a texture map

#define MAX_LIGHTS 5

// Uniform variables passed in from host program
uniform vec3 kd;
uniform vec4 radiance[MAX_LIGHTS];

// Variables passed in from the vertex shader
in float ndotl[MAX_LIGHTS];

// Output variable, will be written to framebuffer automatically
out vec4 frag_shaded;

void main() {
	
	//Diffuse
	vec4 diffuse[MAX_LIGHTS];
	for (int i = 0; i < MAX_LIGHTS; i++) {
		diffuse[i] = radiance[i] * ndotl[i] * vec4(kd,0);
	}
	vec4 dif = diffuse[0];
	for (int i = 1; i < MAX_LIGHTS; i++) {
		dif = dif + diffuse[i];
	}
	
	vec4 neon = dif;
	if (neon.x > 0.6)
		neon.x = 1;
	if (neon.y > 0.6)
		neon.y = 1;
	if (neon.z > 0.6)
		neon.z = 1;
	
	frag_shaded = neon;
}
