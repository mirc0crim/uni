#version 150
// GLSL version 1.50
// Fragment shader for test2 shading

// Variables passed in from the vertex shader
in vec3 color;

// Output variable, will be written to framebuffer automatically
out vec4 frag_shaded;

void main() {
	frag_shaded = vec4(color,0);
}
