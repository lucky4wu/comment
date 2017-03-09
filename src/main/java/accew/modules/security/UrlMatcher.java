package accew.modules.security;

public abstract interface UrlMatcher {
	public abstract Object compile(String paramString);

	public abstract boolean pathMatchesUri(Object paramObject, String paramString);

	public abstract String getUniversalMatchPattern();

	public abstract boolean requiresLowerCaseUrl();
}
