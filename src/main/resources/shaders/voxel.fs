#version 330

uniform sampler2D voxelTexture;

in vec2 textureCoordinates;

out vec4 fragColor;

void main() { 
	fragColor = texture(voxelTexture, textureCoordinates);
}