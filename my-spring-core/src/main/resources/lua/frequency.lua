local key = KEYS[1]
local duration = tonumber(ARGV[1])
local count = tonumber(ARGV[2])

if (duration <= 0 or count <= 0) then
    return 0
end

local function current_time_millis()
    local time = redis.call('time')
    return time[1] * 1000 + math.floor(time[2] / 1000)
end

local size = redis.call('llen', key)
if (size >= count) then
    local earliest_time = redis.call('lindex', key, 0)
    if earliest_time then
        if (current_time_millis() - earliest_time) < duration * 1000 then
            return 0
        else
            redis.call('lpop', key)
        end
    end
end
redis.call('rpush', key, current_time_millis())
redis.call('expire', key, duration)
return 1