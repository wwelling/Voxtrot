#version 330

uniform samplerCube cubeTexture;

in vec3 textureCoordinates;

out vec4 fragColor;

void main() {
	fragColor = texture(cubeTexture, textureCoordinates);
}