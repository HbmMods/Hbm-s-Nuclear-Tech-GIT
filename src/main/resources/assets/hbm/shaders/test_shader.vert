#version 330 compatibility

out vec2 texCoord;
out vec2 lightCoord;
out vec4 color;
out vec3 lighting;

void main(){
	gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
	texCoord = (gl_TextureMatrix[0] * gl_MultiTexCoord0).st;
	lightCoord = (gl_TextureMatrix[1] * gl_MultiTexCoord1).st;
	color = gl_Color;

	vec3 totalLighting = vec3(gl_LightModel.ambient) * vec3(gl_FrontMaterial.emission);
	vec3 normal = (gl_NormalMatrix * gl_Normal).xyz;
	vec4 difftot = vec4(0.0F);

	for (int i = 0; i < gl_MaxLights; i ++){

		vec4 diff = gl_FrontLightProduct[i].diffuse * max(dot(normal,gl_LightSource[i].position.xyz), 0.0f);
		diff = clamp(diff, 0.0F, 1.0F);

		difftot += diff;
	}
	lighting = clamp((difftot + gl_LightModel.ambient).rgb, 0.0F, 1.0F);
}
