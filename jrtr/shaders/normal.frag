#version 150

// Fragment shader for pseudo shading using the normal 

in vec4 frag_normal;
in vec4 frag_color;

// Output variable, will be written to framebuffer automatically
out vec4 frag_shaded;

void main()
{		
	// Scale color according to z component of camera space normal
	frag_shaded = frag_normal.z * frag_color;		
}
