ignore %r{.nfs\h+}, %r{.swp$}, %r{~$}

guard 'coffeescript', :output => 'build', :all_on_start => true, :error_to_js => true do
  watch(%r{^src/(.+\.coffee$)})
end

# SASS
guard 'sass', :output => 'build', :all_on_start => true, :compass =>true do
  watch(%r{^src/(.+\.scss$)})
end
#End SASS


guard 'jade', :output => 'build', :all_on_start => true do
  watch(%r{^src/(.+\.jade$)})
end

guard :copy, :from => 'src', :to => 'build', :mkpath => true, :run_at_start => true, :verbose =>true do
	watch(%r{^src/(?!.+(\.coffee|jade|scss)$)(.+$)})	
end
