#version 330

uniform mat4 view;
uniform mat4 projection;

in vec3 vertex;

out vec3 textureCoordinates;

void main() {
	textureCoordinates = vertex;
	gl_Position = projection * view * vec4(vertex, 1.0);
}