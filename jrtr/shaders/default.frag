#version 150
// Default fragment shader

// Input variable, passed from vertex to fragment shader
// and interpolated automatically to each fragment
in vec4 frag_color;

// Output variable, will be written to framebuffer automatically
out vec4 out_color;

void main()
{		
	out_color = frag_color;		
}
