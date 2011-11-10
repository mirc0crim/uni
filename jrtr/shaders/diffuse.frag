#version 150
// GLSL version 1.50
// Fragment shader for diffuse shading in combination with a texture map

// Uniform variables passed in from host program
uniform sampler2D myTexture;
uniform vec3 kd;
uniform vec3 ka;
uniform vec3 ca;

// Variables passed in from the vertex shader
in float ndotl;
in vec2 frag_texcoord;

// Output variable, will be written to framebuffer automatically
out vec4 frag_shaded;

void main()
{		
	// The built-in GLSL function "texture" performs the texture lookup
	// frag_shaded = ndotl * texture(myTexture, frag_texcoord);
	frag_shaded = ndotl * texture(myTexture, frag_texcoord) * vec4(kd,0) + (vec4(ka,0) * vec4(ca,0));
}
